import { apiRequest } from "../../lib/http-client";
import type { AuthSession, AuthUser } from "../../lib/auth-session";

export type LoginCredentials = { email: string; password: string };
export type RegisterDetails = LoginCredentials & {
	fullName: string;
	currency: string;
};

type LoginResponse = { token: string; user: AuthUser };

export function login(credentials: LoginCredentials): Promise<LoginResponse> {
	return apiRequest<LoginResponse>("/api/auth/login", {
		method: "POST",
		body: JSON.stringify(credentials),
	});
}

export function register(details: RegisterDetails): Promise<AuthUser> {
	return apiRequest<AuthUser>("/api/auth/register", {
		method: "POST",
		body: JSON.stringify(details),
	});
}

export function toAuthSession(response: LoginResponse): AuthSession {
	return { token: response.token, user: response.user };
}
