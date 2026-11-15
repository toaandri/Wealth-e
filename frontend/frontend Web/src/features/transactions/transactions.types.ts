// Type d'opération : un transfert interne n'est NI un revenu NI une dépense (README)
export type TransactionKind = "income" | "expense" | "transfer";

export type TransactionStatus = "posted" | "pending";

export type Transaction = {
  id: string;
  date: string;                 // date de l'opération (format ISO)
  description: string;          // ex. « Salaire novembre »
  kind: TransactionKind;
  amount: number;               // toujours positif : le sens est donné par "kind"
  currency: string;
  accountId: string;            // compte concerné (ou compte de départ pour un transfert)
  toAccountId: string | null;   // compte d'arrivée, seulement pour un transfert
  category: string | null;      // null = non catégorisée → affichée « — »
  status: TransactionStatus;    // "pending" = en attente
};

export type TransactionsSummary = {
  periodLabel: string;          // mois civil courant
  totalIncome: number | null;   // calculé depuis les opérations REST, transferts exclus
  totalExpenses: number | null; // calculé depuis les opérations REST, transferts exclus
  netCashFlow: number | null;   // revenus − dépenses
};

export type TransactionsResponse = {
  summary: TransactionsSummary;
  transactions: Transaction[];
  accountNames: Record<string, string>;
  isDemo: boolean;
};
