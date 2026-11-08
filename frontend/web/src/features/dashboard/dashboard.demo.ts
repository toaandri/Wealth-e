import type { DashboardData } from "./dashboard.types";

export const dashboardDemoData: DashboardData = {
  netWorth: 80_000_000,
  totalAssets: 90_000_000,
  totalLiabilities: 10_000_000,
  periodChangePercentage: 4.2,
  assetCategoryCount: 5,
  loanCount: 3,
  wealthHistory: [
    { label: "Jan", value: 38 },
    { label: "Fév", value: 49 },
    { label: "Mar", value: 43 },
    { label: "Avr", value: 57 },
    { label: "Mai", value: 52 },
    { label: "Juin", value: 68 },
    { label: "Juil", value: 65 },
    { label: "Août", value: 75 },
    { label: "Sep", value: 72 },
    { label: "Oct", value: 81 },
    { label: "Nov", value: 89 },
    { label: "Déc", value: 95 }
  ],
  assetAllocation: [
    {
      id: "demo-property",
      label: "Immobilier",
      percentage: 89
    },
    {
      id: "demo-savings",
      label: "Épargne",
      percentage: 8
    },
    {
      id: "demo-investments",
      label: "Investissements",
      percentage: 3
    }
  ],
  isDemo: true,
  generatedAt: "2026-11-06T10:00:00Z"
};
