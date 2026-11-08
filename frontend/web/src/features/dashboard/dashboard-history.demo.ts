import type { DashboardPeriod } from "./dashboard.types";

export type WealthPoint = {
  label: string;
  value: number; // patrimoine net en Ar
};

export type WealthHistory = {
  periodLabel: string;
  changePercentage: number | null;
  points: WealthPoint[];
};

// Simule la réponse du backend : GET /dashboard/history?period=...
// Les montants et l'évolution viennent "de l'API" : le front ne les calcule pas.
export const demoHistoryByPeriod: Record<DashboardPeriod, WealthHistory> = {
  "1M": {
    periodLabel: "sur 1 mois",
    changePercentage: 1.0,
    points: [
      { label: "Sem 1", value: 79_200_000 },
      { label: "Sem 2", value: 79_500_000 },
      { label: "Sem 3", value: 79_700_000 },
      { label: "Sem 4", value: 80_000_000 },
    ],
  },
  "3M": {
    periodLabel: "sur 3 mois",
    changePercentage: 1.4,
    points: [
      { label: "Oct", value: 78_900_000 },
      { label: "Nov", value: 79_400_000 },
      { label: "Déc", value: 80_000_000 },
    ],
  },
  "1Y": {
    periodLabel: "sur 12 mois",
    changePercentage: 4.2,
    points: [
      { label: "Jan", value: 76_800_000 },
      { label: "Fév", value: 77_300_000 },
      { label: "Mar", value: 77_000_000 },
      { label: "Avr", value: 77_800_000 },
      { label: "Mai", value: 77_500_000 },
      { label: "Juin", value: 78_300_000 },
      { label: "Juil", value: 78_100_000 },
      { label: "Août", value: 78_700_000 },
      { label: "Sep", value: 78_500_000 },
      { label: "Oct", value: 78_900_000 },
      { label: "Nov", value: 79_400_000 },
      { label: "Déc", value: 80_000_000 },
    ],
  },
  ALL: {
    periodLabel: "depuis 2021",
    changePercentage: 29.0,
    points: [
      { label: "2021", value: 62_000_000 },
      { label: "2022", value: 66_500_000 },
      { label: "2023", value: 71_000_000 },
      { label: "2024", value: 74_200_000 },
      { label: "2025", value: 76_800_000 },
      { label: "2026", value: 80_000_000 },
    ],
  },
};
