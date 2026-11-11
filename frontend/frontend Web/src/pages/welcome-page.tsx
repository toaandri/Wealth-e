import { Link } from "react-router-dom";

export function WelcomePage() {
	return (
		<main className="welcome-page">
			<nav className="welcome-nav" aria-label="Navigation">
				<Link className="welcome-brand" to="/" aria-label="Wealth-e, accueil">
					<span className="welcome-brand__mark" aria-hidden="true">W</span><span>WEALTH-E</span>
				</Link>
				<div className="welcome-nav__links">
					<a href="#fonctionnalites">Découvrir</a>
					<Link className="welcome-nav__login" to="/login">Se connecter</Link>
				</div>
			</nav>

			<section className="welcome-hero" aria-labelledby="welcome-title">
				<div className="welcome-hero__copy">
					<p className="welcome-eyebrow">Votre argent, en toute clarté</p>
					<h1 id="welcome-title">Prenez le contrôle de <span>votre avenir.</span></h1>
					<p className="welcome-hero__description">
						Rassemblez vos comptes, suivez vos dépenses et avancez vers vos objectifs financiers depuis un seul endroit.
					</p>
					<div className="welcome-actions">
						<Link className="welcome-start" to="/login">Commencez</Link>
						<span className="welcome-actions__note">Simple, clair et pensé pour vous.</span>
					</div>
				</div>

				<div className="welcome-visual" aria-label="Aperçu du tableau de bord Wealth-e">
					<div className="welcome-visual__glow" />
					<div className="welcome-card welcome-card--balance" aria-hidden="true">
						<p className="welcome-card__label">Vue d’ensemble</p>
						<strong className="welcome-card__amount">Votre patrimoine</strong>
						<div className="welcome-card__chart"><span /><span /><span /><span /><span /><span /></div>
					</div>
					<div className="welcome-card welcome-card--goal" aria-hidden="true">
						<p className="welcome-card__label">Un objectif à la fois</p>
						<div className="welcome-card__goal-row"><span className="welcome-card__goal-icon">↗</span><span>Projet en cours</span></div>
						<div className="welcome-card__progress"><span /></div>
					</div>
				</div>
			</section>

			<section className="welcome-features" id="fonctionnalites" aria-labelledby="features-title">
				<div className="welcome-features__inner">
					<h2 className="welcome-features__heading" id="features-title">Une vision plus sereine de vos finances</h2>
					<div className="welcome-feature-grid">
						<article className="welcome-feature">
							<span className="welcome-feature__icon" aria-hidden="true">◫</span>
							<h3>Vos comptes réunis</h3>
							<p>Consultez vos comptes et soldes au même endroit pour mieux comprendre votre situation.</p>
						</article>
						<article className="welcome-feature">
							<span className="welcome-feature__icon" aria-hidden="true">⌁</span>
							<h3>Vos dépenses suivies</h3>
							<p>Gardez un œil sur vos mouvements et repérez plus facilement vos habitudes.</p>
						</article>
						<article className="welcome-feature">
							<span className="welcome-feature__icon" aria-hidden="true">◎</span>
							<h3>Vos projets en chemin</h3>
							<p>Donnez une direction à votre épargne avec des objectifs qui comptent pour vous.</p>
						</article>
					</div>
				</div>
			</section>
			<footer className="welcome-footer">Wealth-e · Gérez votre patrimoine à votre rythme.</footer>
		</main>
	);
}
