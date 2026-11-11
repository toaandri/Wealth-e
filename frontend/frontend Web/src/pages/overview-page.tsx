import { useEffect, useState } from "react";
import { MoneyValue } from "../components/money-value";
import { LoadingSkeleton } from "../components/loading-skeleton";
import { ErrorState } from "../components/error-state";
import { getDashboard, type DashboardResponse } from "../features/dashboard/dashboard.api";
import { getAuthSession } from "../lib/auth-session";
import "../styles/dashboard.css";

export function OverviewPage() {
  const userId = getAuthSession()?.user.id;
  const currency = getAuthSession()?.user.currency ?? "MGA";
  const [data, setData] = useState<DashboardResponse | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(false);
  const [refreshKey, setRefreshKey] = useState(0);

  useEffect(() => {
    if (!userId) return;
    let active = true;
    setIsLoading(true);
    setError(false);
    getDashboard(userId)
      .then((response) => { if (active) setData(response); })
      .catch(() => { if (active) setError(true); })
      .finally(() => { if (active) setIsLoading(false); });
    return () => { active = false; };
  }, [userId, refreshKey]);

  if (isLoading) {
    return <div className="dashboard-page"><div className="dashboard-container"><LoadingSkeleton lines={6} label="Chargement du tableau de bord…" /></div></div>;
  }

  if (error || !data) {
    return (
      <div className="dashboard-page"><div className="dashboard-container">
        <ErrorState title="Impossible de charger le tableau de bord" message="Vérifiez que le serveur REST est démarré, puis réessayez." onRetry={() => setRefreshKey((key) => key + 1)} />
      </div></div>
    );
  }

  return (
    <div className="dashboard-page">
      <div className="dashboard-container">
        <header className="dashboard-header">
          <div>
            <p className="dashboard-breadcrumb">Patrimoine / Tableau de bord</p>
            <h1>Tableau de bord</h1>
            <p className="dashboard-subtitle">Synthèse de vos données financières</p>
          </div>
        </header>

        <section className="kpi-grid" aria-label="Indicateurs patrimoniaux">
          <article className="dashboard-card kpi-card">
            <p className="kpi-label">Patrimoine net</p>
            <MoneyValue amount={data.netWorth} currency={currency} className="kpi-value" />
            <p className="kpi-meta">Actifs moins dettes</p>
          </article>

          <article className="dashboard-card kpi-card">
            <p className="kpi-label">Actifs</p>
            <MoneyValue amount={data.totalAssets} currency={currency} className="kpi-value" />
            <p className="kpi-meta">{data.accountCount} comptes · {data.investmentCount} investissements · {data.assetCount} autres actifs</p>
          </article>

          <article className="dashboard-card kpi-card">
            <p className="kpi-label">Dettes</p>
            <MoneyValue amount={data.totalLiabilities} currency={currency} className="kpi-value" />
            <p className="kpi-meta">{data.liabilityCount} éléments</p>
          </article>
        </section>

        <section className="dashboard-content-grid">
          <article className="dashboard-card allocation-card">
            <div className="card-heading"><h2>Activité financière</h2></div>
            <dl className="overview-stats">
              <div><dt>Solde des comptes</dt><dd><MoneyValue amount={data.totalBalance} currency={currency} /></dd></div>
              <div><dt>Revenus enregistrés</dt><dd><MoneyValue amount={data.totalIncome} currency={currency} /></dd></div>
              <div><dt>Dépenses enregistrées</dt><dd><MoneyValue amount={data.totalExpenses} currency={currency} /></dd></div>
              <div><dt>Transactions</dt><dd>{data.transactionCount}</dd></div>
            </dl>
          </article>
          <article className="dashboard-card allocation-card">
            <div className="card-heading"><h2>Modules renseignés</h2></div>
            <dl className="overview-stats">
              <div><dt>Budgets</dt><dd>{data.budgetCount}</dd></div>
              <div><dt>Objectifs</dt><dd>{data.goalCount}</dd></div>
              <div><dt>Investissements</dt><dd>{data.investmentCount}</dd></div>
              <div><dt>Autres actifs</dt><dd>{data.assetCount}</dd></div>
            </dl>
          </article>
        </section>

        <footer className="dashboard-footer">
          WEALTH-E · Synthèse issue des données de votre compte · Release v1.0.0-core
        </footer>
      </div>
    </div>
  );
}
