import { apiRequest } from "../../lib/http-client";
import type { Account, AccountType, AccountsResponse } from "./accounts.types";

export type CreateAccountInput = {
	userId: number;
	name: string;
	type: AccountType;
	currency: string;
	balance: number;
};

type BackendAccount = {
	id: number;
	name: string;
	type: "CASH" | "BANK" | "MOBILE_MONEY" | "SAVINGS" | "INVESTMENT" | "OTHER";
	balance: number | null;
	currency: string;
	userId: number;
};

export const ACCOUNT_TYPE_LABELS: Record<AccountType, string> = {
	cash: "Espèces",
	checking: "Compte courant",
	mobile_money: "Mobile money",
	savings: "Épargne",
	investment: "Investissement",
	other: "Autre",
};

const ACCOUNT_TYPE_MAP: Record<BackendAccount["type"], AccountType> = {
	CASH: "cash",
	BANK: "checking",
	MOBILE_MONEY: "mobile_money",
	SAVINGS: "savings",
	INVESTMENT: "investment",
	OTHER: "other",
};

const BACKEND_ACCOUNT_TYPE_MAP: Record<AccountType, BackendAccount["type"]> = {
	cash: "CASH",
	checking: "BANK",
	mobile_money: "MOBILE_MONEY",
	savings: "SAVINGS",
	investment: "INVESTMENT",
	other: "OTHER",
};

function mapAccount(account: BackendAccount): Account {
	return {
		id: String(account.id),
		name: account.name,
		type: ACCOUNT_TYPE_MAP[account.type],
		balance: account.balance,
		currency: account.currency,
		userId: account.userId,
	};
}

export async function getAccounts(userId: number): Promise<AccountsResponse> {
	const response = await apiRequest<BackendAccount[]>(`/api/accounts?userId=${encodeURIComponent(userId)}`);
	return { accounts: response.map(mapAccount) };
}

export async function getAccountById(id: string): Promise<Account> {
	const response = await apiRequest<BackendAccount>(`/api/accounts/${encodeURIComponent(id)}`);
	return mapAccount(response);
}

export async function createAccount(input: CreateAccountInput): Promise<Account> {
	const response = await apiRequest<BackendAccount>("/api/accounts", {
		method: "POST",
		body: JSON.stringify({
			...input,
			type: BACKEND_ACCOUNT_TYPE_MAP[input.type],
		}),
	});
	return mapAccount(response);
}
