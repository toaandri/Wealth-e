import { NavLink } from "react-router-dom";
import { NAV_ITEMS } from "../app/navigation";

const MOBILE_ITEMS = NAV_ITEMS.filter((item) => item.mobile);

function linkClass({ isActive }: { isActive: boolean }) {
  return "bottom-nav__link" + (isActive ? " is-active" : "");
}

export function BottomNavigation() {
  return (
    <nav className="bottom-nav" aria-label="Navigation mobile">
      <ul className="bottom-nav__list">
        {MOBILE_ITEMS.map((item) => (
          <li key={item.to}>
            <NavLink to={item.to} className={linkClass}>
              {item.shortLabel ?? item.label}
            </NavLink>
          </li>
        ))}
        <li>
          <NavLink to="/more" className={linkClass}>Plus</NavLink>
        </li>
      </ul>
    </nav>
  );
}
