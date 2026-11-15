import type { Account } from "./accounts.types";

// Fixture utilisée par les écrans de démonstration.
export const accountsDemoData: { accounts: Account[] } = {
  accounts: [
    {
      id: "demo-checking",
      name: "Compte courant",
      type: "checking",
      balance: 2_450_000,
      currency: "MGA",
      userId: 1,
    },
    {
      id: "demo-savings",
      name: "Livret d'épargne",
      type: "savings",
      balance: 3_800_000,
      currency: "MGA",
      userId: 1,
    },
    {
      id: "demo-mobile-money",
      name: "Portefeuille mobile",
      type: "mobile_money",
      balance: 650_000,
      currency: "MGA",
      userId: 1,
    },
    {
      id: "demo-cash",
      name: "Espèces",
      type: "cash",
      balance: 300_000,
      currency: "MGA",
      userId: 1,
    },
  ],
};
