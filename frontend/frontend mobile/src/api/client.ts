import type { Account, Transaction } from "../model";

const API_BASE_URL = (process.env.EXPO_PUBLIC_API_BASE_URL ?? "http://localhost:8080").replace(/\/$/, "");

export type AuthUser = {
	id: number;
	email: string;
	fullName: string;
	currency: string;
};

export type AuthSession = {
	token: string;
	user: AuthUser;
};

type BackendAccount = {
	id: number;
	name: string;
	type: "CASH" | "BANK" | "MOBILE_MONEY" | "SAVINGS" | "INVESTMENT" | "OTHER";
	balance: number | null;
	currency: string;
	userId: number;
};

type BackendTransaction = {
	id: number;
	accountId: number;
	amount: number;
	type: "INCOME" | "EXPENSE";
	category: string;
	description: string | null;
	transactionDate: string;
};

type ApiErrorPayload = { message?: string; detail?: string };

async function apiRequest<T>(path: string, token?: string, init: RequestInit = {}): Promise<T> {
	const headers = new Headers(init.headers);
	headers.set("Accept", "application/json");
	if (init.body) headers.set("Content-Type", "application/json");
	if (token) headers.set("Authorization", `Bearer ${token}`);

	let response: Response;
	try {
		response = await fetch(`${API_BASE_URL}${path}`, { ...init, headers });
	} catch {
		throw new Error("Le serveur Wealth-e est injoignable. Vérifiez l’adresse API et que le serveur est démarré.");
	}

	const responseText = await response.text();
	let payload: unknown = null;
	if (responseText) {
		try {
			payload = JSON.parse(responseText) as unknown;
		} catch {
			if (response.ok) throw new Error("Le serveur a renvoyé une réponse invalide.");
		}
	}
	if (!response.ok) {
		const error = payload as ApiErrorPayload | null;
		throw new Error(error?.message || error?.detail || `La requête a échoué (${response.status}).`);
	}
	return payload as T;
}

export function loginUser(email: string, password: string): Promise<AuthSession> {
	return apiRequest<AuthSession>("/api/auth/login", undefined, {
		method: "POST",
		body: JSON.stringify({ email, password }),
	});
}

export function registerUser(input: { fullName: string; email: string; password: string; currency: string }): Promise<AuthUser> {
	return apiRequest<AuthUser>("/api/auth/register", undefined, {
		method: "POST",
		body: JSON.stringify(input),
	});
}

const ACCOUNT_KIND: Record<BackendAccount["type"], string> = {
	CASH: "Espèces",
	BANK: "Courant",
	MOBILE_MONEY: "Mobile money",
	SAVINGS: "Épargne",
	INVESTMENT: "Courtage",
	OTHER: "Autre",
};

export async function getCoreData(userId: number, token: string): Promise<{ accounts: Account[]; transactions: Transaction[] }> {
	const query = `userId=${encodeURIComponent(userId)}`;
	const [accounts, transactions] = await Promise.all([
		apiRequest<BackendAccount[]>(`/api/accounts?${query}`, token),
		apiRequest<BackendTransaction[]>(`/api/transactions?${query}`, token),
	]);

	return {
		accounts: accounts.map((account) => ({
			id: String(account.id),
			name: account.name,
			balance: Number(account.balance ?? 0),
			kind: ACCOUNT_KIND[account.type],
		})),
		transactions: transactions.map((transaction) => ({
			id: String(transaction.id),
			name: transaction.description || transaction.category,
			amount: transaction.type === "INCOME" ? Number(transaction.amount) : -Number(transaction.amount),
			category: transaction.category,
			date: transaction.transactionDate,
			account: String(transaction.accountId),
		})),
	};
}

const ACCOUNT_TYPE: Record<string, BackendAccount["type"]> = {
	"Courant": "BANK",
	"Épargne": "SAVINGS",
	"Courtage": "INVESTMENT",
	"Espèces": "CASH",
	"Mobile money": "MOBILE_MONEY",
	"Autre": "OTHER",
};

export function createRemoteAccount(input: {
	userId: number;
	token: string;
	name: string;
	kind: string;
	currency: string;
	balance: number;
}): Promise<BackendAccount> {
	return apiRequest<BackendAccount>("/api/accounts", input.token, {
		method: "POST",
		body: JSON.stringify({
			userId: input.userId,
			name: input.name,
			type: ACCOUNT_TYPE[input.kind] ?? "OTHER",
			currency: input.currency,
			balance: input.balance,
		}),
	});
}

export function createRemoteTransaction(input: {
	token: string;
	accountId: number;
	amount: number;
	type: "INCOME" | "EXPENSE";
	category: string;
	description: string;
	transactionDate: string;
}): Promise<BackendTransaction> {
	const { token, ...body } = input;
	return apiRequest<BackendTransaction>("/api/transactions", token, {
		method: "POST",
		body: JSON.stringify(body),
	});
}
