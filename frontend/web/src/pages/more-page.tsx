import { Link } from "react-router-dom";
import { NAV_ITEMS } from "../app/navigation";

export function MorePage() {
  return (
    <section className="page-placeholder">
      <h1>Toutes les rubriques</h1>
      <ul className="more-list">
        {NAV_ITEMS.map((item) => (
          <li key={item.to}>
            <Link to={item.to} className="more-list__link">{item.label}</Link>
          </li>
        ))}
      </ul>
    </section>
  );
}
