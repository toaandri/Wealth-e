import { createBrowserRouter, Navigate } from "react-router-dom";
import { AppShell } from "./app-shell";
import { NAV_ITEMS } from "./navigation";
import { OverviewPage } from "../pages/overview-page";
import { ComingSoonPage } from "../pages/coming-soon-page";
import { MorePage } from "../pages/more-page";

// Les écrans pas encore développés affichent « À venir »
const placeholderRoutes = NAV_ITEMS
  .filter((item) => item.to !== "/dashboard")
  .map((item) => ({
    path: item.to.slice(1), // "/accounts" → "accounts"
    element: <ComingSoonPage title={item.label} />,
  }));

export const router = createBrowserRouter([
  { path: "/", element: <Navigate to="/dashboard" replace /> },
  {
    element: <AppShell />,
    children: [
      { path: "dashboard", element: <OverviewPage /> },
      ...placeholderRoutes,
      { path: "more", element: <MorePage /> },
      { path: "*", element: <ComingSoonPage title="Page introuvable (404)" /> },
    ],
  },
]);
