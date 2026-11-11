import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { ErrorState } from "../components/error-state";
import { LoadingSkeleton } from "../components/loading-skeleton";
import { MoneyValue } from "../components/money-value";
import { ACCOUNT_TYPE_LABELS, getAccounts } from "../features/accounts/accounts.api";
import type { Account } from "../features/accounts/accounts.types";
import { getAuthSession } from "../lib/auth-session";

export function AccountDetailsPage() {
	const { id } = useParams<{ id: string }>();
	const userId = getAuthSession()?.user.id;
	const [account, setAccount] = useState<Account | null>(null);
	const [isLoading, setIsLoading] = useState(true);
	const [error, setError] = useState(false);
	const [refreshKey, setRefreshKey] = useState(0);

	useEffect(() => {
		if (!id || !userId) return;
		let active = true;
		setIsLoading(true);
		setError(false);
		getAccounts(userId)
			.then(({ accounts }) => {
				const currentUserAccount = accounts.find((item) => item.id === id);
				if (!currentUserAccount) throw new Error("Compte introuvable dans votre espace.");
				if (active) setAccount(currentUserAccount);
			})
			.catch(() => { if (active) setError(true); })
			.finally(() => { if (active) setIsLoading(false); });
		return () => { active = false; };
	}, [id, userId, refreshKey]);

	return (
		<div className="page">
			<header className="page-header">
				<div>
					<p className="page-breadcrumb">Patrimoine / Comptes / Détails</p>
					<h1>{account?.name ?? "Détails du compte"}</h1>
					<p className="page-subtitle">Informations renvoyées par le serveur REST.</p>
				</div>
				<Link className="button account-back-button" to="/accounts">Retour aux comptes</Link>
			</header>

			{isLoading ? <div className="card"><LoadingSkeleton lines={4} label="Chargement du compte…" /></div> : null}
			{error ? <ErrorState title="Impossible de charger ce compte" message="Vérifiez l’identifiant du compte, puis réessayez." onRetry={() => setRefreshKey((key) => key + 1)} /> : null}
			{!isLoading && !error && account ? (
				<section className="card account-details-card" aria-label="Informations du compte">
					<div className="account-details-card__balance">
						<p className="summary-card__label">Solde actuel</p>
						<MoneyValue amount={account.balance} currency={account.currency} className="summary-card__value" />
					</div>
					<dl className="account-details-list">
						<div><dt>Nom du compte</dt><dd>{account.name}</dd></div>
						<div><dt>Identifiant du compte</dt><dd>{account.id}</dd></div>
						<div><dt>Type</dt><dd>{ACCOUNT_TYPE_LABELS[account.type]}</dd></div>
						<div><dt>Devise</dt><dd>{account.currency}</dd></div>
						<div><dt>Identifiant du propriétaire</dt><dd>{account.userId}</dd></div>
					</dl>
					<p className="account-details-note">Le backend REST ne renvoie pas d’établissement bancaire ni de date de mise à jour pour ce compte.</p>
				</section>
			) : null}
		</div>
	);
}
