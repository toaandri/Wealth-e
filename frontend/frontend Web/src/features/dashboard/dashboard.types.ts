export type DashboardPeriod = "1M" | "3M" | "1Y" | "ALL";

export type WealthPoint = {
  label: string;
  value: number;
};

export type AssetAllocationItem = {
  id: string;
  label: string;
  percentage: number;
};

export type DashboardData = {
  netWorth: number | null;
  totalAssets: number | null;
  totalLiabilities: number | null;
  periodChangePercentage: number | null;
  assetCategoryCount: number;
  loanCount: number;
  wealthHistory: WealthPoint[];
  assetAllocation: AssetAllocationItem[];
  isDemo: boolean;
  generatedAt: string;
};
