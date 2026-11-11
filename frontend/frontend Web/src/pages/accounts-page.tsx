import { useEffect, useState, type FormEvent } from "react";
import { Link } from "react-router-dom";
import { MoneyValue } from "../components/money-value";
import { LoadingSkeleton } from "../components/loading-skeleton";
import { EmptyState } from "../components/empty-state";
import { ErrorState } from "../components/error-state";
import { createAccount, getAccounts } from "../features/accounts/accounts.api";
import type { AccountType } from "../features/accounts/accounts.types";
import { getAuthSession } from "../lib/auth-session";

// Libellés en français des types de comptes
const TYPE_LABELS: Record<AccountType, string> = {
  checking: "Compte courant",
  savings: "Épargne",
  mobile_money: "Mobile money",
  cash: "Espèces",
  investment: "Investissement",
  other: "Autre",
};

export function AccountsPage() {
  const userId = getAuthSession()?.user.id;
  const [accounts, setAccounts] = useState<Awaited<ReturnType<typeof getAccounts>>["accounts"]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(false);
  const [refreshKey, setRefreshKey] = useState(0);
  const [showCreateForm, setShowCreateForm] = useState(false);
  const [newAccountName, setNewAccountName] = useState("");
  const [newAccountType, setNewAccountType] = useState<AccountType>("checking");
  const [newAccountBalance, setNewAccountBalance] = useState("0");
  const [formError, setFormError] = useState("");
  const [isCreating, setIsCreating] = useState(false);

  async function handleCreateAccount(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    if (!userId) return;
    setFormError("");
    setIsCreating(true);
    try {
      await createAccount({
        userId,
        name: newAccountName.trim(),
        type: newAccountType,
        currency: getAuthSession()?.user.currency ?? "MGA",
        balance: Number(newAccountBalance),
      });
      setNewAccountName("");
      setNewAccountType("checking");
      setNewAccountBalance("0");
      setShowCreateForm(false);
      setRefreshKey((key) => key + 1);
    } catch (requestError) {
      setFormError(requestError instanceof Error ? requestError.message : "La création du compte a échoué.");
    } finally {
      setIsCreating(false);
    }
  }

  useEffect(() => {
    if (!userId) return;
    let active = true;
    setIsLoading(true);
    setError(false);
    getAccounts(userId)
      .then((response) => {
        if (active) setAccounts(response.accounts);
      })
      .catch(() => {
        if (active) setError(true);
      })
      .finally(() => {
        if (active) setIsLoading(false);
      });
    return () => { active = false; };
  }, [userId, refreshKey]);

  function renderContent() {
    if (isLoading) {
      return (
        <div className="card">
          <LoadingSkeleton lines={5} label="Chargement des comptes…" />
        </div>
      );
    }

    if (error) {
      return (
        <ErrorState
          title="Impossible de charger vos comptes"
          message="Vérifiez que le serveur REST est démarré, puis réessayez."
          onRetry={() => setRefreshKey((key) => key + 1)}
        />
      );
    }

    if (accounts.length === 0) {
      return (
        <EmptyState
          title="Aucun compte pour l'instant"
          message="Créez un compte pour commencer à suivre votre patrimoine."
        />
      );
    }

    return (
      <>
        <section className="card summary-card" aria-label="Nombre de comptes">
          <p className="summary-card__label">Comptes enregistrés</p>
          <strong className="summary-card__value">{accounts.length}</strong>
          <p className="summary-card__meta">Données chargées depuis votre espace Wealth-e</p>
        </section>

        <section aria-labelledby="accounts-list-title">
          <h2 id="accounts-list-title" className="section-title">Mes comptes</h2>

          <ul className="item-list">
            {accounts.map((account) => (
              <li key={account.id} className="card item-card">
                <div className="item-card__main">
                  <Link className="item-card__title account-title-link" to={`/accounts/${account.id}`}>{account.name}</Link>
                  <p className="item-card__meta">
                    {TYPE_LABELS[account.type]} · {account.currency}
                  </p>
                  <p className="item-card__meta">N° {account.id} · Utilisateur {account.userId}</p>
                </div>

                <div className="item-card__side">
                  <MoneyValue amount={account.balance} currency={account.currency} className="item-card__amount" />
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
          <p className="page-subtitle">Comptes et soldes enregistrés dans votre espace.</p>
        </div>
        <button className="button" type="button" onClick={() => setShowCreateForm((visible) => !visible)}>
          {showCreateForm ? "Annuler" : "Créer un compte"}
        </button>
      </header>

      {showCreateForm ? (
        <form className="card account-create-form" onSubmit={handleCreateAccount}>
          <h2 className="section-title">Nouveau compte</h2>
          <label className="auth-field">
            Nom du compte
            <input value={newAccountName} onChange={(event) => setNewAccountName(event.target.value)} required maxLength={120} />
          </label>
          <label className="auth-field">
            Type de compte
            <select value={newAccountType} onChange={(event) => setNewAccountType(event.target.value as AccountType)}>
              {Object.entries(TYPE_LABELS).map(([type, label]) => <option key={type} value={type}>{label}</option>)}
            </select>
          </label>
          <label className="auth-field">
            Solde initial ({getAuthSession()?.user.currency ?? "MGA"})
            <input type="number" step="0.01" value={newAccountBalance} onChange={(event) => setNewAccountBalance(event.target.value)} required />
          </label>
          {formError ? <p className="auth-form-error" role="alert">{formError}</p> : null}
          <button className="button" type="submit" disabled={isCreating}>{isCreating ? "Création…" : "Enregistrer le compte"}</button>
        </form>
      ) : null}

      {renderContent()}
    </div>
  );
}
