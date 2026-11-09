import { useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { DemoDataBadge } from "../components/demo-data-badge";
import { MoneyValue } from "../components/money-value";
import { LoadingSkeleton } from "../components/loading-skeleton";
import { EmptyState } from "../components/empty-state";
import { ErrorState } from "../components/error-state";
import { dashboardDemoData } from "../features/dashboard/dashboard.demo";
import { demoHistoryByPeriod } from "../features/dashboard/dashboard-history.demo";
import type { DashboardPeriod } from "../features/dashboard/dashboard.types";
import "../styles/dashboard.css";

const periods: DashboardPeriod[] = ["1M", "3M", "1Y", "ALL"];

const amountFormatter = new Intl.NumberFormat("fr-FR");
const millionFormatter = new Intl.NumberFormat("fr-FR", { maximumFractionDigits: 1 });

function formatAmount(value: number) {
  return `${amountFormatter.format(value)} Ar`;
}

function formatMillions(value: number) {
  return `${millionFormatter.format(value / 1_000_000)} M Ar`;
}

function formatChange(percentage: number | null) {
  if (percentage === null) return { text: "Évolution : —", tone: "" };
  const value = Math.abs(percentage).toFixed(1).replace(".", ",");
  if (percentage > 0) return { text: `↗ +${value} %`, tone: "kpi-meta--positive" };
  if (percentage < 0) return { text: `↘ −${value} %`, tone: "kpi-meta--negative" };
  return { text: "→ 0,0 %", tone: "" };
}

export function OverviewPage() {
  const [selectedPeriod, setSelectedPeriod] = useState<DashboardPeriod>("1Y");
    const navigate = useNavigate();
  const [searchParams, setSearchParams] = useSearchParams();

  // Test des états via l'adresse (?state=loading|empty|error), en développement uniquement
  const demoState = import.meta.env.DEV ? searchParams.get("state") : null;


  const data = dashboardDemoData;

  // Données de la période choisie
  const history = demoHistoryByPeriod[selectedPeriod];
  const points = history.points;
  const hasData = points.length > 0;
  const change = formatChange(history.changePercentage);

  // Échelle du graphique (affichage uniquement)
  const values = points.map((point) => point.value);
  const minValue = hasData ? Math.min(...values) : 0;
  const maxValue = hasData ? Math.max(...values) : 0;
  const range = maxValue - minValue;

  function barHeight(value: number) {
    if (range === 0) return 100;
    return 15 + ((value - minValue) / range) * 85;
  }

  // Libellés de l'axe : début, milieu, fin
  const axisLabels =
    points.length > 2
      ? [points[0], points[Math.floor((points.length - 1) / 2)], points[points.length - 1]]
      : points;

  return (
    <div className="dashboard-page">
      <div className="dashboard-container">
        <header className="dashboard-header">
          <div>
            <p className="dashboard-breadcrumb">Patrimoine / Tableau de bord</p>
            <h1>Tableau de bord</h1>
            <p className="dashboard-subtitle">Synthèse du patrimoine net</p>
          </div>

          {data.isDemo ? <DemoDataBadge /> : null}
        </header>

        <section className="kpi-grid" aria-label="Indicateurs patrimoniaux">
          <article className="dashboard-card kpi-card">
            <p className="kpi-label">Patrimoine net</p>
            <MoneyValue amount={data.netWorth} className="kpi-value" />
            <p className={`kpi-meta ${change.tone}`}>
              {change.text} {history.periodLabel}
            </p>
          </article>

          <article className="dashboard-card kpi-card">
            <p className="kpi-label">Actifs</p>
            <MoneyValue amount={data.totalAssets} className="kpi-value" />
            <p className="kpi-meta">{data.assetAllocation.length} catégories</p>
          </article>

          <article className="dashboard-card kpi-card">
            <p className="kpi-label">Dettes</p>
            <MoneyValue amount={data.totalLiabilities} className="kpi-value" />
            <p className="kpi-meta">{data.loanCount} prêts</p>
          </article>
        </section>

        <section className="dashboard-content-grid">
          <article className="dashboard-card wealth-card">
            <div className="card-heading">
              <h2>Évolution du patrimoine</h2>
            </div>

            <div className="period-filter" role="group" aria-label="Période du graphique">
              {periods.map((period) => (
                <button
                  key={period}
                  type="button"
                  className={
                    selectedPeriod === period
                      ? "period-button period-button--selected"
                      : "period-button"
                  }
                  aria-pressed={selectedPeriod === period}
                  onClick={() => setSelectedPeriod(period)}
                >
                  {period}
                </button>
              ))}
            </div>

            {hasData ? (
              <>
                <div
                  className="wealth-chart"
                  role="img"
                  aria-label={`Évolution du patrimoine net ${history.periodLabel}, de ${formatAmount(points[0].value)} à ${formatAmount(points[points.length - 1].value)}`}
                >
                  <div className="chart-grid-lines" aria-hidden="true">
                    <span />
                    <span />
                    <span />
                  </div>

                  <div className="chart-bars" aria-hidden="true">
                    {points.map((point) => (
                      <div
                        key={point.label}
                        className="chart-bar"
                        style={{ height: `${barHeight(point.value)}%` }}
                        title={`${point.label} : ${formatAmount(point.value)}`}
                      />
                    ))}
                  </div>
                </div>

                <div className="chart-axis" aria-hidden="true">
                  {axisLabels.map((point) => (
                    <span key={point.label}>{point.label}</span>
                  ))}
                </div>

                <p className="chart-scale">
                  Échelle : de {formatMillions(minValue)} à {formatMillions(maxValue)} (l'axe ne
                  part pas de 0)
                </p>

                <table className="visually-hidden">
                  <caption>Évolution du patrimoine net {history.periodLabel}</caption>
                  <thead>
                    <tr>
                      <th scope="col">Période</th>
                      <th scope="col">Patrimoine net</th>
                    </tr>
                  </thead>
                  <tbody>
                    {points.map((point) => (
                      <tr key={point.label}>
                        <th scope="row">{point.label}</th>
                        <td>{formatAmount(point.value)}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </>
            ) : (
              <p className="chart-empty">Aucune donnée pour cette période.</p>
            )}
          </article>

          <article className="dashboard-card allocation-card">
            <div className="card-heading">
              <h2>Répartition</h2>
            </div>

            <div className="allocation-table-wrapper">
              <table className="allocation-table">
                <thead>
                  <tr>
                    <th scope="col">Actif</th>
                    <th scope="col">Part</th>
                  </tr>
                </thead>
                <tbody>
                  {data.assetAllocation.map((item) => (
                    <tr key={item.id}>
                      <td>{item.label}</td>
                      <td>{item.percentage} %</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </article>
        </section>

        <footer className="dashboard-footer">
          WEALTH-E · Maquette UX/UI conceptuelle · Données 100 % synthétiques · Pas de conseil
          financier · Release v1.0.0-core
        </footer>
      </div>
    </div>
  );
}
