// Point d entree React.
import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { App } from "./app/app";
import "@fontsource-variable/inter";
import "./styles/global.css";
import "./styles/components.css";


const rootElement = document.getElementById("root");

if (!rootElement) {
  throw new Error("L'élément #root est introuvable.");
}

createRoot(rootElement).render(
  <StrictMode>
    <App />
  </StrictMode>
);
