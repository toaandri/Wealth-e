export type AccountType = "checking" | "savings" | "mobile_money" | "cash" | "investment" | "other";

export type Account = {
  id: string;
  name: string;                  // ex. « Compte courant »
  type: AccountType;
  balance: number | null;
  currency: string;
  userId: number;
};

export type AccountsResponse = {
  accounts: Account[];
};
