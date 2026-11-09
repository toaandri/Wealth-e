import { useSearchParams } from "react-router-dom";
import { DemoDataBadge } from "../components/demo-data-badge";
import { MoneyValue } from "../components/money-value";
import { LoadingSkeleton } from "../components/loading-skeleton";
import { EmptyState } from "../components/empty-state";
import { ErrorState } from "../components/error-state";
import { accountsDemoData } from "../features/accounts/accounts.demo";
import type { AccountType } from "../features/accounts/accounts.types";

// Libellés en français des types de comptes
const TYPE_LABELS: Record<AccountType, string> = {
  checking: "Compte courant",
  savings: "Épargne",
  mobile_money: "Mobile money",
  cash: "Espèces",
};

const dateFormatter = new Intl.DateTimeFormat("fr-FR", {
  dateStyle: "medium",
  timeStyle: "short",
});

export function AccountsPage() {
  const [searchParams, setSearchParams] = useSearchParams();

  // Test des états via l'adresse (?state=loading|empty|error), en développement uniquement
  const demoState = import.meta.env.DEV ? searchParams.get("state") : null;

  const data = accountsDemoData;
  const accounts = demoState === "empty" ? [] : data.accounts;

  function renderContent() {
    if (demoState === "loading") {
      return (
        <div className="card">
          <LoadingSkeleton lines={5} label="Chargement des comptes…" />
        </div>
      );
    }

    if (demoState === "error") {
      return (
        <ErrorState
          title="Impossible de charger vos comptes"
          onRetry={() => setSearchParams({})}
        />
      );
    }

    if (accounts.length === 0) {
      return (
        <EmptyState
          title="Aucun compte pour l'instant"
          message="Vos comptes de démonstration apparaîtront ici."
        />
      );
    }

    return (
      <>
        <section className="card summary-card" aria-label="Solde total des comptes">
          <p className="summary-card__label">Solde total</p>
          <MoneyValue amount={data.summary.totalBalance} className="summary-card__value" />
          <p className="summary-card__meta">{data.summary.accountCount} comptes</p>
        </section>

        <section aria-labelledby="accounts-list-title">
          <h2 id="accounts-list-title" className="section-title">Mes comptes</h2>

          <ul className="item-list">
            {accounts.map((account) => (
              <li key={account.id} className="card item-card">
                <div className="item-card__main">
                  <p className="item-card__title">{account.name}</p>
                  <p className="item-card__meta">
                    {TYPE_LABELS[account.type]} · {account.institution}
                  </p>
                </div>

                <div className="item-card__side">
                  <MoneyValue amount={account.balance} className="item-card__amount" />
                  <p className="item-card__meta">
                    Mis à jour :{" "}
                    {account.lastUpdatedAt ? (
                      <time dateTime={account.lastUpdatedAt}>
                        {dateFormatter.format(new Date(account.lastUpdatedAt))}
                      </time>
                    ) : (
                      "—"
                    )}
                  </p>
                </div>
              </li>
            ))}
          </ul>
        </section>
      </>
    );
  }

  return (
    <div className="page">
      <header className="page-header">
        <div>
          <p className="page-breadcrumb">Patrimoine / Comptes</p>
          <h1>Comptes</h1>
          <p className="page-subtitle">Soldes de vos comptes de démonstration</p>
        </div>
        {data.isDemo ? <DemoDataBadge /> : null}
      </header>

      {renderContent()}
    </div>
  );
}
