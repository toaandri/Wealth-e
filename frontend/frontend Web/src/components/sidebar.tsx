import { NavLink } from "react-router-dom";
import { NAV_ITEMS } from "../app/navigation";
import { LogoutButton } from "./logout-button";

export function Sidebar() {
  return (
    <aside className="sidebar">
      <div className="brand">
        <span className="brand__logo" aria-hidden="true">W</span>
        <span className="brand__name">WEALTH-E</span>
      </div>

      <nav aria-label="Navigation principale">
        <ul className="sidebar__list">
          {NAV_ITEMS.map((item) => (
            <li key={item.to}>
              <NavLink
                to={item.to}
                className={({ isActive }) => "sidebar__link" + (isActive ? " is-active" : "")}
              >
                {item.label}
              </NavLink>
            </li>
          ))}
        </ul>
      </nav>

      <LogoutButton />
      <p className="sidebar__footer">Release v1.0.0-core</p>
    </aside>
  );
}
