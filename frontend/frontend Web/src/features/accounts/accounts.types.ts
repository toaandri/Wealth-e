export type AccountType = "checking" | "savings" | "mobile_money" | "cash";

export type Account = {
  id: string;
  name: string;                  // ex. « Compte courant »
  type: AccountType;
  institution: string;           // banque fictive (données de démo)
  balance: number | null;        // null = inconnu → affiché « — », jamais 0
  currency: "MGA";
  lastUpdatedAt: string | null;  // date de mise à jour (null = inconnue)
};

export type AccountsSummary = {
  totalBalance: number | null;   // total calculé par le BACKEND, pas par le front
  accountCount: number;
};

export type AccountsResponse = {
  summary: AccountsSummary;
  accounts: Account[];
  isDemo: boolean;
};
