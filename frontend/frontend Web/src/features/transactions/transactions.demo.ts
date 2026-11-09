import type { TransactionsResponse } from "./transactions.types";

// Simule la réponse du backend : GET /transactions?period=2026-11
// Toutes les données sont fictives (DEMO DATA).
// Les opérations sont triées par le backend, de la plus récente à la plus ancienne.
export const transactionsDemoData: TransactionsResponse = {
  summary: {
    periodLabel: "Novembre 2026",
    totalIncome: 1_920_000,   // 1 800 000 + 120 000 (le transfert est exclu)
    totalExpenses: 250_000,   // 185 000 + 25 000 + 40 000 (le transfert et l'opération en attente sont exclus)
    netCashFlow: 1_670_000,   // 1 920 000 − 250 000
  },
  transactions: [
    {
      id: "demo-tx-7",
      date: "2026-11-06",
      description: "Facture électricité",
      kind: "expense",
      amount: 95_000,
      currency: "MGA",
      accountId: "demo-checking",
      toAccountId: null,
      category: "Logement",
      status: "pending", // en attente : pas encore comptée dans les totaux
    },
    {
      id: "demo-tx-6",
      date: "2026-11-05",
      description: "Vente d'objets d'occasion",
      kind: "income",
      amount: 120_000,
      currency: "MGA",
      accountId: "demo-mobile-money",
      toAccountId: null,
      category: "Autres revenus",
      status: "posted",
    },
    {
      id: "demo-tx-5",
      date: "2026-11-05",
      description: "Trajet taxi-brousse",
      kind: "expense",
      amount: 40_000,
      currency: "MGA",
      accountId: "demo-cash",
      toAccountId: null,
      category: null, // non catégorisée → affichée « — »
      status: "posted",
    },
    {
      id: "demo-tx-4",
      date: "2026-11-04",
      description: "Recharge crédit téléphonique",
      kind: "expense",
      amount: 25_000,
      currency: "MGA",
      accountId: "demo-mobile-money",
      toAccountId: null,
      category: "Télécom",
      status: "posted",
    },
    {
      id: "demo-tx-3",
      date: "2026-11-03",
      description: "Virement vers le livret d'épargne",
      kind: "transfer", // transfert interne : NI revenu NI dépense
      amount: 500_000,
      currency: "MGA",
      accountId: "demo-checking",
      toAccountId: "demo-savings",
      category: "Transfert interne",
      status: "posted",
    },
    {
      id: "demo-tx-2",
      date: "2026-11-02",
      description: "Courses au supermarché",
      kind: "expense",
      amount: 185_000,
      currency: "MGA",
      accountId: "demo-checking",
      toAccountId: null,
      category: "Alimentation",
      status: "posted",
    },
    {
      id: "demo-tx-1",
      date: "2026-11-01",
      description: "Salaire novembre",
      kind: "income",
      amount: 1_800_000,
      currency: "MGA",
      accountId: "demo-checking",
      toAccountId: null,
      category: "Salaire",
      status: "posted",
    },
  ],
  isDemo: true,
};
