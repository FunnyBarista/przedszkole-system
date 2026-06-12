import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../App.css";

export default function MojeDzieciPage() {
  const [dzieci, setDzieci] = useState([]);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  const navigate = useNavigate();

  function getSession() {
    const token = localStorage.getItem("token");

    if (!token) {
      return null;
    }

    try {
      const payload = JSON.parse(atob(token.split(".")[1]));
      return { token, rola: payload.rola, email: payload.sub };
    } catch {
      return null;
    }
  }

  async function loadDzieci() {
    setError("");
    setLoading(true);

    const session = getSession();

    if (!session) {
      setError("Brak tokena. Najpierw sie zaloguj.");
      setLoading(false);
      return;
    }

    const url = ["ADMIN", "DYREKCJA"].includes(session.rola)
      ? "http://localhost:8080/api/admin/dzieci"
      : "http://localhost:8080/api/rodzice/me/dzieci";

    try {
      const res = await fetch(url, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${session.token}`
        }
      });

      if (!res.ok) {
        throw new Error("Nie udalo sie pobrac danych dzieci.");
      }

      const data = await res.json();
      setDzieci(Array.isArray(data) ? data : []);
    } catch (e) {
      setError(e.message);
      setDzieci([]);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    loadDzieci();
  }, []);

  function logout() {
    localStorage.removeItem("token");
    navigate("/");
  }

  const session = getSession();
  const rodzic = session?.rola === "RODZIC";

  return (
    <div className="shell">
      <div className="card" style={{ maxWidth: 980, margin: "0 auto", width: "100%" }}>
        <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", gap: 12 }}>
          <div>
            <h3>{rodzic ? "Moje dzieci" : "Panel dzieci"}</h3>
            <div className="empty">
              {rodzic
                ? "Podglad obecnosci i statusu pobytu dziecka."
                : "Lista dzieci w systemie przedszkola."}
            </div>
          </div>

          <button className="btn" type="button" onClick={logout}>
            Wyloguj
          </button>
        </div>

        {loading && (
          <div className="empty" style={{ marginTop: 14 }}>
            Ladowanie danych...
          </div>
        )}

        {error && (
          <div className="error" style={{ marginTop: 12 }}>
            {error}
          </div>
        )}

        {!loading && !error && dzieci.length === 0 && (
          <div className="empty" style={{ marginTop: 14 }}>
            Brak przypisanych dzieci.
          </div>
        )}

        {!loading && !error && dzieci.length > 0 && rodzic && (
          <div className="child-card-grid">
            {dzieci.map((dziecko) => (
              <article className="child-card" key={dziecko.id_dziecka}>
                <div>
                  <span className={statusClass(dziecko.status_pobytu)}>
                    {dziecko.status_pobytu || "brak statusu"}
                  </span>
                  <h4>{dziecko.imie} {dziecko.nazwisko}</h4>
                  <p>Data urodzenia: {dziecko.data_urodzenia}</p>
                </div>
              </article>
            ))}
          </div>
        )}

        {!loading && !error && dzieci.length > 0 && !rodzic && (
          <div className="table-wrap" style={{ marginTop: 14 }}>
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Imie</th>
                  <th>Nazwisko</th>
                  <th>Data urodzenia</th>
                  <th>Status pobytu</th>
                </tr>
              </thead>

              <tbody>
                {dzieci.map((d) => (
                  <tr key={d.id_dziecka}>
                    <td data-label="ID">{d.id_dziecka}</td>
                    <td data-label="Imie">{d.imie}</td>
                    <td data-label="Nazwisko">{d.nazwisko}</td>
                    <td data-label="Data urodzenia">{d.data_urodzenia}</td>
                    <td data-label="Status pobytu">{d.status_pobytu}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
}

function statusClass(status = "") {
  const normalized = status.toLowerCase();

  if (normalized.includes("obec")) {
    return "status-pill present";
  }

  if (normalized.includes("odebrane") || normalized.includes("nieobec")) {
    return "status-pill away";
  }

  return "status-pill";
}
