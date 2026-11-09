import { useState } from "react";
import { useSearchParams } from "react-router-dom";
import { DemoDataBadge } from "../components/demo-data-badge";
import { MoneyValue } from "../components/money-value";
import { LoadingSkeleton } from "../components/loading-skeleton";
import { EmptyState } from "../components/empty-state";
import { ErrorState } from "../components/error-state";
import { transactionsDemoData } from "../features/transactions/transactions.demo";
import { accountsDemoData } from "../features/accounts/accounts.demo";
import type { Transaction, TransactionKind } from "../features/transactions/transactions.types";

type Filter = "all" | TransactionKind;

const FILTERS: { value: Filter; label: string }[] = [
  { value: "all", label: "Tout" },
  { value: "income", label: "Revenus" },
  { value: "expense", label: "Dépenses" },
  { value: "transfer", label: "Transferts" },
];

// Affichage de chaque type d'opération
const KIND_DISPLAY: Record<TransactionKind, { sign: string; label: string; className: string }> = {
  income: { sign: "+", label: "Revenu", className: "tx-amount--income" },
  expense: { sign: "−", label: "Dépense", className: "tx-amount--expense" },
  transfer: { sign: "⇄", label: "Transfert interne", className: "tx-amount--transfer" },
};

// Les dates sont au format "AAAA-MM-JJ" : on les lit en UTC pour éviter un décalage d'un jour
const dateFormatter = new Intl.DateTimeFormat("fr-FR", { dateStyle: "medium", timeZone: "UTC" });

// Nom des comptes à partir de leur identifiant
const accountNames = new Map(accountsDemoData.accounts.map((account) => [account.id, account.name]));

function accountName(id: string) {
  return accountNames.get(id) ?? "Compte inconnu";
}

function accountLabel(transaction: Transaction) {
  if (transaction.kind === "transfer" && transaction.toAccountId) {
    return `${accountName(transaction.accountId)} → ${accountName(transaction.toAccountId)}`;
  }
  return accountName(transaction.accountId);
}

export function TransactionsPage() {
  const [filter, setFilter] = useState<Filter>("all");
  const [searchParams, setSearchParams] = useSearchParams();

  // Test des états via l'adresse (?state=loading|empty|error), en développement uniquement
  const demoState = import.meta.env.DEV ? searchParams.get("state") : null;

  const data = transactionsDemoData;
  const transactions = demoState === "empty" ? [] : data.transactions;

  // Filtre d'AFFICHAGE uniquement : les totaux restent ceux du backend
  const visibleTransactions =
    filter === "all" ? transactions : transactions.filter((tx) => tx.kind === filter);

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
                  <MoneyValue amount={tx.amount} />
                </p>
              </div>
            </li>
          );
        })}
      </ul>
    );
  }

  function renderContent() {
    if (demoState === "loading") {
      return (
        <div className="card">
          <LoadingSkeleton lines={6} label="Chargement des transactions…" />
        </div>
      );
    }

    if (demoState === "error") {
      return (
        <ErrorState
          title="Impossible de charger vos transactions"
          onRetry={() => setSearchParams({})}
        />
      );
    }

    if (transactions.length === 0) {
      return (
        <EmptyState
          title="Aucune transaction pour l'instant"
          message="Les opérations de vos comptes de démonstration apparaîtront ici."
        />
      );
    }

    return (
      <>
        <section className="summary-grid" aria-label={`Totaux de ${data.summary.periodLabel}`}>
          <article className="card summary-card">
            <p className="summary-card__label">Revenus</p>
            <MoneyValue amount={data.summary.totalIncome} className="summary-card__value" />
          </article>
          <article className="card summary-card">
            <p className="summary-card__label">Dépenses</p>
            <MoneyValue amount={data.summary.totalExpenses} className="summary-card__value" />
          </article>
          <article className="card summary-card">
            <p className="summary-card__label">Solde net</p>
            <MoneyValue amount={data.summary.netCashFlow} className="summary-card__value" />
          </article>
        </section>

        <p className="page-note">
          Les transferts internes et les opérations en attente ne sont pas comptés dans les totaux.
        </p>

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
          <p className="page-subtitle">{data.summary.periodLabel}</p>
        </div>
        {data.isDemo ? <DemoDataBadge /> : null}
      </header>

      {renderContent()}
    </div>
  );
}
