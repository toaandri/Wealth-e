import { createBrowserRouter } from "react-router-dom";
import { AppShell } from "./app-shell";
import { NAV_ITEMS } from "./navigation";
import { RequireAuth } from "../features/auth/require-auth";
import { OverviewPage } from "../pages/overview-page";
import { AccountsPage } from "../pages/accounts-page";
import { AccountDetailsPage } from "../pages/account-details-page";
import { ComingSoonPage } from "../pages/coming-soon-page";
import { MorePage } from "../pages/more-page";
import { LoginPage } from "../pages/login-page";
import { RegisterPage } from "../pages/register-page";
import { WelcomePage } from "../pages/welcome-page";

// Pages déjà développées : elles n'affichent plus « À venir »
const IMPLEMENTED_ROUTES = ["/dashboard", "/accounts"];

// Les autres pages du menu affichent « À venir »
const placeholderRoutes = NAV_ITEMS
  .filter((item) => !IMPLEMENTED_ROUTES.includes(item.to))
  .map((item) => ({
    path: item.to.slice(1), // "/budget" → "budget"
    element: <ComingSoonPage title={item.label} />,
  }));

export const router = createBrowserRouter([
  { path: "/", element: <WelcomePage /> },
  { path: "/login", element: <LoginPage /> },
  { path: "/register", element: <RegisterPage /> },
  {
    element: <RequireAuth><AppShell /></RequireAuth>,
    children: [
      { path: "dashboard", element: <OverviewPage /> },
      { path: "accounts", element: <AccountsPage /> },
      { path: "accounts/:id", element: <AccountDetailsPage /> },
      ...placeholderRoutes,
      { path: "more", element: <MorePage /> },
      { path: "*", element: <ComingSoonPage title="Page introuvable (404)" /> },
    ],
  },
]);
