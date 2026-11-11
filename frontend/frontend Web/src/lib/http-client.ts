import { getAuthSession } from "./auth-session";

const API_BASE_URL = (import.meta.env.VITE_API_BASE_URL ?? "http://localhost:8080").replace(/\/$/, "");

type ApiErrorPayload = { message?: string };

export async function apiRequest<T>(path: string, init: RequestInit = {}): Promise<T> {
	const headers = new Headers(init.headers);
	headers.set("Accept", "application/json");
	if (init.body) headers.set("Content-Type", "application/json");

	const session = getAuthSession();
	if (session?.token) headers.set("Authorization", `Bearer ${session.token}`);

	let response: Response;
	try {
		response = await fetch(`${API_BASE_URL}${path}`, { ...init, headers });
	} catch {
		throw new Error("Le serveur Wealth-e est injoignable. Vérifiez qu'il est démarré, puis réessayez.");
	}

	const responseText = await response.text();
	const payload = responseText ? (JSON.parse(responseText) as unknown) : null;
	if (!response.ok) {
		const message = (payload as ApiErrorPayload | null)?.message;
		throw new Error(message || `La requête a échoué (${response.status}).`);
	}

	return payload as T;
}
