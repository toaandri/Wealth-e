package com.wealthe;

import com.wealthe.config.AuthTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureMockMvc
class WealthEApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthTokenService tokenService;

    @Test
    void contextLoads() {
    }

    @Test
    void apiRejectsRequestsWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/api/assets").param("userId", "1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void authenticatedAssetListUsesValidH2Schema() throws Exception {
        mockMvc.perform(get("/api/assets")
                        .param("userId", "1")
                        .header("Authorization", "Bearer " + tokenService.issue(1L)))
                .andExpect(status().isOk());
    }

    @Test
    void authenticatedUserCannotClaimAnotherUserId() throws Exception {
        mockMvc.perform(get("/api/accounts")
                        .param("userId", "2")
                        .header("Authorization", "Bearer " + tokenService.issue(1L)))
                .andExpect(status().isForbidden());
    }
}
