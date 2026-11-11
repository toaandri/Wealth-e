import { useState, type FormEvent } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { login, toAuthSession } from "../features/auth/auth.api";
import { saveAuthSession } from "../lib/auth-session";

type LoginLocationState = { from?: string };

export function LoginPage() {
	const navigate = useNavigate();
	const location = useLocation();
	const destination = (location.state as LoginLocationState | null)?.from ?? "/dashboard";
	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");
	const [error, setError] = useState("");
	const [isSubmitting, setIsSubmitting] = useState(false);

	async function handleSubmit(event: FormEvent<HTMLFormElement>) {
		event.preventDefault();
		setError("");
		setIsSubmitting(true);
		try {
			const response = await login({ email: email.trim(), password });
			saveAuthSession(toAuthSession(response));
			navigate(destination, { replace: true });
		} catch (requestError) {
			setError(requestError instanceof Error ? requestError.message : "Connexion impossible.");
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
					<p className="auth-brand-panel__eyebrow">Vos finances, votre rythme</p>
					<h2>Restez maître de vos projets.</h2>
					<p>Retrouvez votre espace financier et continuez à avancer vers ce qui compte.</p>
				</div>
				<p className="auth-brand-panel__footer">Un espace conçu pour vous aider à y voir plus clair.</p>
			</section>

			<section className="auth-form-panel">
				<div className="auth-form-card">
					<Link className="auth-back-link" to="/">← Retour à l’accueil</Link>
					<h1>Bon retour</h1>
					<p className="auth-form-card__intro">Connectez-vous pour accéder à vos comptes.</p>
					<form className="auth-form" onSubmit={handleSubmit}>
						<label className="auth-field">
							Adresse e-mail
							<input autoComplete="email" type="email" value={email} onChange={(event) => setEmail(event.target.value)} required />
						</label>
						<label className="auth-field">
							Mot de passe
							<input autoComplete="current-password" type="password" value={password} onChange={(event) => setPassword(event.target.value)} required />
						</label>
						{error ? <p className="auth-form-error" role="alert">{error}</p> : null}
						<button className="auth-submit" type="submit" disabled={isSubmitting}>
							{isSubmitting ? "Connexion…" : "Se connecter"}
						</button>
					</form>
					<p className="auth-form-foot">Pas encore de compte ? <Link to="/register">Créer un compte</Link></p>
				</div>
			</section>
		</main>
	);
}
