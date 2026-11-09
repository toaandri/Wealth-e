import type { AccountsResponse } from "./accounts.types";

// Simule la réponse du backend : GET /accounts
// Toutes les données sont fictives (DEMO DATA).
export const accountsDemoData: AccountsResponse = {
  summary: {
    totalBalance: 7_200_000, // calculé par le backend
    accountCount: 4,
  },
  accounts: [
    {
      id: "demo-checking",
      name: "Compte courant",
      type: "checking",
      institution: "Banque Démo Alpha",
      balance: 2_450_000,
      currency: "MGA",
      lastUpdatedAt: "2026-11-06T09:30:00Z",
    },
    {
      id: "demo-savings",
      name: "Livret d'épargne",
      type: "savings",
      institution: "Banque Démo Alpha",
      balance: 3_800_000,
      currency: "MGA",
      lastUpdatedAt: "2026-11-05T18:00:00Z",
    },
    {
      id: "demo-mobile-money",
      name: "Portefeuille mobile",
      type: "mobile_money",
      institution: "Opérateur Démo",
      balance: 650_000,
      currency: "MGA",
      lastUpdatedAt: "2026-11-06T08:15:00Z",
    },
    {
      id: "demo-cash",
      name: "Espèces",
      type: "cash",
      institution: "Saisie manuelle",
      balance: 300_000,
      currency: "MGA",
      lastUpdatedAt: null, // date inconnue → affichée « — »
    },
  ],
  isDemo: true,
};
