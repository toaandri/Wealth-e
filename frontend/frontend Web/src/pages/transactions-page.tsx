import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import { MoneyValue } from "../components/money-value";
import { LoadingSkeleton } from "../components/loading-skeleton";
import { EmptyState } from "../components/empty-state";
import { ErrorState } from "../components/error-state";
import { getTransactions } from "../features/transactions/transactions.api";
import type { Transaction, TransactionKind } from "../features/transactions/transactions.types";
import { getAuthSession } from "../lib/auth-session";

type Filter = "all" | TransactionKind;

const FILTERS: { value: Filter; label: string }[] = [
  { value: "all", label: "Tout" },
  { value: "income", label: "Revenus" },
  { value: "expense", label: "Dépenses" },
  { value: "transfer", label: "Transferts" },
];

const KIND_DISPLAY: Record<TransactionKind, { sign: string; label: string; className: string }> = {
  income: { sign: "+", label: "Revenu", className: "tx-amount--income" },
  expense: { sign: "−", label: "Dépense", className: "tx-amount--expense" },
  transfer: { sign: "⇄", label: "Transfert interne", className: "tx-amount--transfer" },
};

const dateFormatter = new Intl.DateTimeFormat("fr-FR", { dateStyle: "medium", timeZone: "UTC" });

export function TransactionsPage() {
  const [filter, setFilter] = useState<Filter>("all");
  const [searchParams, setSearchParams] = useSearchParams();
  const [refreshKey, setRefreshKey] = useState(0);
  const [data, setData] = useState<Awaited<ReturnType<typeof getTransactions>> | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState("");
  const session = getAuthSession();
  const userId = session?.user.id;
  const currency = session?.user.currency ?? "MGA";

  const demoState = import.meta.env.DEV ? searchParams.get("state") : null;

  useEffect(() => {
    if (!userId) {
      setIsLoading(false);
      setError("Reconnectez-vous pour consulter vos transactions.");
      return;
    }

    let active = true;
    setIsLoading(true);
    setError("");
    getTransactions(userId, currency)
      .then((response) => { if (active) setData(response); })
      .catch((requestError: unknown) => {
        if (active) setError(requestError instanceof Error ? requestError.message : "Le chargement des transactions a échoué.");
      })
      .finally(() => { if (active) setIsLoading(false); });
    return () => { active = false; };
  }, [userId, currency, refreshKey]);

  const transactions = demoState === "empty" ? [] : data?.transactions ?? [];
  const visibleTransactions = filter === "all" ? transactions : transactions.filter((tx) => tx.kind === filter);
  const accountLabel = (transaction: Transaction) => {
    const source = data?.accountNames[transaction.accountId] ?? "Compte inconnu";
    if (transaction.kind === "transfer" && transaction.toAccountId) {
      const destination = data?.accountNames[transaction.toAccountId] ?? "Compte inconnu";
      return `${source} → ${destination}`;
    }
    return source;
  };

  function renderList() {
    if (visibleTransactions.length === 0) {
      return <EmptyState title="Aucune opération de ce type sur la période" />;
    }

    return (
      <ul className="item-list">
        {visibleTransactions.map((tx) => {
          const display = KIND_DISPLAY[tx.kind];
          return (
            <li key={tx.id} className="card item-card">
              <div className="item-card__main">
                <p className="item-card__title">
                  {tx.description}
                  {tx.status === "pending" ? <span className="status-badge">En attente</span> : null}
                </p>
                <p className="item-card__meta">
                  <time dateTime={tx.date}>{dateFormatter.format(new Date(tx.date))}</time>
                  {" · "}
                  {display.label} · {tx.category ?? "—"}
                </p>
                <p className="item-card__meta">{accountLabel(tx)}</p>
              </div>
              <div className="item-card__side">
                <p className={`tx-amount ${display.className}`}>
                  <span aria-hidden="true">{display.sign} </span>
                  <MoneyValue amount={tx.amount} currency={tx.currency} />
                </p>
              </div>
            </li>
          );
        })}
      </ul>
    );
  }

  function renderContent() {
    if (isLoading || demoState === "loading") {
      return <div className="card"><LoadingSkeleton lines={6} label="Chargement des transactions…" /></div>;
    }

    if (demoState === "error" || error) {
      return (
        <ErrorState
          title="Impossible de charger vos transactions"
          message={demoState === "error" ? undefined : error}
          onRetry={() => {
            if (demoState === "error") setSearchParams({});
            setRefreshKey((key) => key + 1);
          }}
        />
      );
    }

    if (!data || transactions.length === 0) {
      return <EmptyState title="Aucune transaction pour l'instant" message="Les opérations enregistrées dans votre espace apparaîtront ici." />;
    }

    return (
      <>
        <section className="summary-grid" aria-label={`Totaux de ${data.summary.periodLabel}`}>
          <article className="card summary-card">
            <p className="summary-card__label">Revenus</p>
            <MoneyValue amount={data.summary.totalIncome} currency={currency} className="summary-card__value" />
          </article>
          <article className="card summary-card">
            <p className="summary-card__label">Dépenses</p>
            <MoneyValue amount={data.summary.totalExpenses} currency={currency} className="summary-card__value" />
          </article>
          <article className="card summary-card">
            <p className="summary-card__label">Solde net</p>
            <MoneyValue amount={data.summary.netCashFlow} currency={currency} className="summary-card__value" />
          </article>
        </section>

        <p className="page-note">Les transferts internes et les opérations en attente ne sont pas comptés dans les totaux.</p>
        <section aria-labelledby="tx-list-title">
          <h2 id="tx-list-title" className="section-title">Opérations</h2>
          <div className="filter-group" role="group" aria-label="Filtrer par type d'opération">
            {FILTERS.map((item) => (
              <button
                key={item.value}
                type="button"
                className={filter === item.value ? "filter-button is-selected" : "filter-button"}
                aria-pressed={filter === item.value}
                onClick={() => setFilter(item.value)}
              >
                {item.label}
              </button>
            ))}
          </div>
          {renderList()}
        </section>
      </>
    );
  }

  return (
    <div className="page">
      <header className="page-header">
        <div>
          <p className="page-breadcrumb">Patrimoine / Transactions</p>
          <h1>Transactions</h1>
          <p className="page-subtitle">{data?.summary.periodLabel ?? "Transactions du compte"}</p>
        </div>
      </header>
      {renderContent()}
    </div>
  );
}
