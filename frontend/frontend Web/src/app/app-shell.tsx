import { Outlet } from "react-router-dom";
import { Sidebar } from "../components/sidebar";
import { BottomNavigation } from "../components/bottom-navigation";
import "../styles/layout.css";

export function AppShell() {
  return (
    <div className="app-shell">
      <a href="#main-content" className="skip-link">Aller au contenu</a>

      <Sidebar />

      <header className="topbar">
        <span className="brand__logo" aria-hidden="true">W</span>
        <span className="brand__name">WEALTH-E</span>
      </header>

      <main id="main-content" className="app-shell__main" tabIndex={-1}>
        <Outlet />
      </main>

      <BottomNavigation />
    </div>
  );
}
