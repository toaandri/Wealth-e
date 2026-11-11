import { useNavigate } from "react-router-dom";
import { clearAuthSession } from "../lib/auth-session";

export function LogoutButton() {
  const navigate = useNavigate();

  function logout() {
    clearAuthSession();
    navigate("/login", { replace: true });
  }

  return (
    <button className="logout-button" type="button" onClick={logout}>
      Se déconnecter
    </button>
  );
}
