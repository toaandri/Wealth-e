export type NavItem = {
  to: string;
  label: string;
  shortLabel?: string; // libellé court pour la barre du bas
  mobile?: boolean;    // affiché dans la barre du bas
};

export const NAV_ITEMS: NavItem[] = [
  { to: "/dashboard", label: "Tableau de bord", shortLabel: "Accueil", mobile: true },
  { to: "/accounts", label: "Comptes", mobile: true },
  { to: "/transactions", label: "Transactions", mobile: true },
  { to: "/budget", label: "Budget", mobile: true },
  { to: "/investments", label: "Investissements" },
  { to: "/property", label: "Immobilier" },
  { to: "/liabilities", label: "Dettes" },
  { to: "/analytics", label: "Analyses" },
  { to: "/reports", label: "Rapports" },
  { to: "/scenarios", label: "Scénarios" },
  { to: "/connections", label: "Connexions" },
  { to: "/settings", label: "Paramètres" },
];
