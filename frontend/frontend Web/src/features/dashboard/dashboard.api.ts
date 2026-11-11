import { apiRequest } from "../../lib/http-client";

export type DashboardResponse = {
	userId: number;
	totalBalance: number;
	totalIncome: number;
	totalExpenses: number;
	accountCount: number;
	transactionCount: number;
	totalAssets: number;
	totalLiabilities: number;
	netWorth: number;
	budgetCount: number;
	goalCount: number;
	investmentCount: number;
	assetCount: number;
	liabilityCount: number;
};

export function getDashboard(userId: number): Promise<DashboardResponse> {
	return apiRequest<DashboardResponse>(`/api/dashboard?userId=${encodeURIComponent(userId)}`);
}
