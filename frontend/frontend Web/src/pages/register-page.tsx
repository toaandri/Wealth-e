import { useState, type FormEvent } from "react";
import { Link, useNavigate } from "react-router-dom";
import { login, register, toAuthSession } from "../features/auth/auth.api";
import { saveAuthSession } from "../lib/auth-session";

export function RegisterPage() {
	const navigate = useNavigate();
	const [fullName, setFullName] = useState("");
	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");
	const [currency, setCurrency] = useState("MGA");
	const [error, setError] = useState("");
	const [isSubmitting, setIsSubmitting] = useState(false);

	async function handleSubmit(event: FormEvent<HTMLFormElement>) {
		event.preventDefault();
		setError("");
		setIsSubmitting(true);
		try {
			await register({ fullName: fullName.trim(), email: email.trim(), password, currency });
			// L’API d’inscription ne renvoie pas de jeton : on ouvre la session via l’API login.
			const response = await login({ email: email.trim(), password });
			saveAuthSession(toAuthSession(response));
			navigate("/dashboard", { replace: true });
		} catch (requestError) {
			setError(requestError instanceof Error ? requestError.message : "Création du compte impossible.");
		} finally {
			setIsSubmitting(false);
		}
	}

	return (
		<main className="auth-page">
			<section className="auth-brand-panel" aria-label="Wealth-e">
				<Link className="auth-brand" to="/">
					<span className="auth-brand__mark" aria-hidden="true">W</span><span>WEALTH-E</span>
				</Link>
				<div className="auth-brand-panel__copy">
					<p className="auth-brand-panel__eyebrow">Le premier pas commence ici</p>
					<h2>Construisez une relation sereine avec votre argent.</h2>
					<p>Créez votre espace personnel et donnez une nouvelle direction à vos projets financiers.</p>
				</div>
				<p className="auth-brand-panel__footer">Votre espace reste accessible après connexion.</p>
			</section>

			<section className="auth-form-panel">
				<div className="auth-form-card">
					<Link className="auth-back-link" to="/">← Retour à l’accueil</Link>
					<h1>Créer un compte</h1>
					<p className="auth-form-card__intro">Quelques informations pour commencer avec Wealth-e.</p>
					<form className="auth-form" onSubmit={handleSubmit}>
						<label className="auth-field">
							Nom complet
							<input autoComplete="name" type="text" value={fullName} onChange={(event) => setFullName(event.target.value)} required />
						</label>
						<label className="auth-field">
							Adresse e-mail
							<input autoComplete="email" type="email" value={email} onChange={(event) => setEmail(event.target.value)} required />
						</label>
						<label className="auth-field">
							Mot de passe <span>(6 caractères minimum)</span>
							<input autoComplete="new-password" type="password" minLength={6} value={password} onChange={(event) => setPassword(event.target.value)} required />
						</label>
						<label className="auth-field">
							Devise principale
							<select value={currency} onChange={(event) => setCurrency(event.target.value)}>
								<option value="MGA">Ariary malgache (MGA)</option>
								<option value="EUR">Euro (EUR)</option>
								<option value="USD">Dollar américain (USD)</option>
							</select>
						</label>
						{error ? <p className="auth-form-error" role="alert">{error}</p> : null}
						<button className="auth-submit" type="submit" disabled={isSubmitting}>
							{isSubmitting ? "Création…" : "Créer mon compte"}
						</button>
					</form>
					<p className="auth-form-foot">Vous avez déjà un compte ? <Link to="/login">Se connecter</Link></p>
				</div>
			</section>
		</main>
	);
}
