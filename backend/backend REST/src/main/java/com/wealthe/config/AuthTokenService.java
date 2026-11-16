package com.wealthe.config;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthTokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthTokenService.class);
    private static final long TOKEN_LIFETIME_SECONDS = 12 * 60 * 60;
    private static final String HMAC_ALGORITHM = "HmacSHA256";

    private final byte[] secret;

    public AuthTokenService(@Value("${app.auth.token-secret:}") String configuredSecret) {
        if (configuredSecret == null || configuredSecret.isBlank()) {
            this.secret = new byte[32];
            new SecureRandom().nextBytes(this.secret);
            LOGGER.warn("No app.auth.token-secret configured; auth tokens will be invalidated at restart.");
        } else {
            if (configuredSecret.length() < 32) {
                throw new IllegalArgumentException("app.auth.token-secret must contain at least 32 characters");
            }
            this.secret = configuredSecret.getBytes(StandardCharsets.UTF_8);
        }
    }

    public String issue(Long userId) {
        long expiresAt = Instant.now().getEpochSecond() + TOKEN_LIFETIME_SECONDS;
        String payload = userId + ":" + expiresAt;
        String encodedPayload = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(payload.getBytes(StandardCharsets.UTF_8));
        return encodedPayload + "." + Base64.getUrlEncoder().withoutPadding()
                .encodeToString(sign(encodedPayload));
    }

    public Optional<Long> userId(String token) {
        if (token == null) {
            return Optional.empty();
        }

        String[] parts = token.split("\\.", -1);
        if (parts.length != 2 || parts[0].isBlank() || parts[1].isBlank()) {
            return Optional.empty();
        }

        try {
            byte[] suppliedSignature = Base64.getUrlDecoder().decode(parts[1]);
            if (!MessageDigest.isEqual(sign(parts[0]), suppliedSignature)) {
                return Optional.empty();
            }

            String payload = new String(Base64.getUrlDecoder().decode(parts[0]), StandardCharsets.UTF_8);
            String[] claims = payload.split(":", -1);
            if (claims.length != 2) {
                return Optional.empty();
            }

            long userId = Long.parseLong(claims[0]);
            long expiresAt = Long.parseLong(claims[1]);
            if (userId <= 0 || expiresAt <= Instant.now().getEpochSecond()) {
                return Optional.empty();
            }
            return Optional.of(userId);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    private byte[] sign(String value) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(new SecretKeySpec(secret, HMAC_ALGORITHM));
            return mac.doFinal(value.getBytes(StandardCharsets.UTF_8));
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Unable to sign authentication token", e);
        }
    }
}
