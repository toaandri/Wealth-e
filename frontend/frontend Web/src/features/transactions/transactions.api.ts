import { apiRequest } from "../../lib/http-client";
import type { Transaction, TransactionsResponse } from "./transactions.types";

type BackendTransaction = {
	id: number;
	accountId: number;
	amount: number;
	type: "INCOME" | "EXPENSE";
	category: string;
	description: string | null;
	transactionDate: string;
};

type BackendAccount = {
	id: number;
	name: string;
};

function isTransfer(category: string): boolean {
	return /transfert/i.test(category);
}

function currentMonthRange() {
	const now = new Date();
	const year = now.getFullYear();
	const month = now.getMonth();
	const dateKey = (date: Date) => `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, "0")}-${String(date.getDate()).padStart(2, "0")}`;
	const start = dateKey(new Date(year, month, 1));
	const end = dateKey(new Date(year, month + 1, 1));
	const periodLabel = new Intl.DateTimeFormat("fr-FR", { month: "long", year: "numeric" }).format(now);
	return { start, end, periodLabel };
}

export async function getTransactions(userId: number, currency: string): Promise<TransactionsResponse> {
	const query = `userId=${encodeURIComponent(userId)}`;
	const [backendTransactions, accounts] = await Promise.all([
		apiRequest<BackendTransaction[]>(`/api/transactions?${query}`),
		apiRequest<BackendAccount[]>(`/api/accounts?${query}`),
	]);
	const transactions: Transaction[] = backendTransactions.map((transaction) => {
		const transfer = isTransfer(transaction.category);
		return {
			id: String(transaction.id),
			date: transaction.transactionDate,
			description: transaction.description || transaction.category,
			kind: transfer ? "transfer" : transaction.type === "INCOME" ? "income" : "expense",
			amount: Number(transaction.amount),
			currency,
			accountId: String(transaction.accountId),
			toAccountId: null,
			category: transaction.category,
			status: "posted",
		};
	});

	const { start, end, periodLabel } = currentMonthRange();
	const periodTransactions = transactions.filter((transaction) => transaction.date >= start && transaction.date < end);
	const totalIncome = periodTransactions
		.filter((transaction) => transaction.kind === "income")
		.reduce((total, transaction) => total + transaction.amount, 0);
	const totalExpenses = periodTransactions
		.filter((transaction) => transaction.kind === "expense")
		.reduce((total, transaction) => total + transaction.amount, 0);

	return {
		transactions,
		accountNames: Object.fromEntries(accounts.map((account) => [String(account.id), account.name])),
		summary: {
			periodLabel,
			totalIncome,
			totalExpenses,
			netCashFlow: totalIncome - totalExpenses,
		},
		isDemo: false,
	};
}
