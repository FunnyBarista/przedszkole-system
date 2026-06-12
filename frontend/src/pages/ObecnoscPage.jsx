import { useEffect, useMemo, useState } from "react";
import "../App.css";

const STATUSY = [
  "obecny w przedszkolu",
  "nieobecny",
  "odebrane przez rodzica"
];

export default function ObecnoscPage() {
  const [dzieci, setDzieci] = useState([]);
  const [loading, setLoading] = useState(true);
  const [blad, setBlad] = useState("");
  const [savingId, setSavingId] = useState(null);
  const [filtr, setFiltr] = useState("");

  const widoczneDzieci = useMemo(() => {
    const tekst = filtr.trim().toLowerCase();

    if (!tekst) {
      return dzieci;
    }

    return dzieci.filter((dziecko) =>
      `${dziecko.imie} ${dziecko.nazwisko} ${dziecko.rodzic || ""}`
        .toLowerCase()
        .includes(tekst)
    );
  }, [dzieci, filtr]);

  async function pobierzDzieci() {
    setLoading(true);
    setBlad("");

    try {
      const response = await fetch("http://localhost:8080/api/dzieci/podglad", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`
        }
      });

      if (!response.ok) {
        throw new Error("Nie udalo sie pobrac listy dzieci.");
      }

      setDzieci(await response.json());
    } catch (error) {
      setBlad(error.message);
      setDzieci([]);
    } finally {
      setLoading(false);
    }
  }

  async function ustawStatus(id, statusPobytu) {
    setSavingId(id);
    setBlad("");

    try {
      const response = await fetch(`http://localhost:8080/api/dzieci/${id}/status`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("token")}`
        },
        body: JSON.stringify({ statusPobytu })
      });

      if (!response.ok) {
        throw new Error("Nie udalo sie zapisac statusu dziecka.");
      }

      const zapisane = await response.json();
      setDzieci((lista) =>
        lista.map((dziecko) => (dziecko.id === zapisane.id ? zapisane : dziecko))
      );
    } catch (error) {
      setBlad(error.message);
    } finally {
      setSavingId(null);
    }
  }

  useEffect(() => {
    pobierzDzieci();
  }, []);

  return (
    <div className="shell">
      <div className="content-grid">
        <section className="card">
          <div className="section-head">
            <div>
              <h3>Obecnosc dzieci</h3>
              <div className="empty">Lista dzieci do oznaczania obecnosci i odbioru.</div>
            </div>
            <div className="presence-count">
              <span>Dzieci</span>
              <strong>{dzieci.length}</strong>
            </div>
          </div>

          <div className="presence-toolbar">
            <input
              aria-label="Szukaj dziecka"
              placeholder="Szukaj dziecka lub rodzica"
              value={filtr}
              onChange={(event) => setFiltr(event.target.value)}
            />
            <button className="btn secondary" type="button" onClick={pobierzDzieci} disabled={loading}>
              Odswiez
            </button>
          </div>

          {loading && <div className="empty">Ladowanie danych...</div>}
          {blad && <div className="error">{blad}</div>}

          {!loading && !blad && widoczneDzieci.length === 0 && (
            <div className="empty">Brak dzieci pasujacych do wyszukiwania.</div>
          )}

          {!loading && widoczneDzieci.length > 0 && (
            <div className="presence-list">
              {widoczneDzieci.map((dziecko) => (
                <article className="presence-row" key={dziecko.id}>
                  <div className="presence-child">
                    <span className={statusClass(dziecko.statusPobytu)}>
                      {dziecko.statusPobytu || "brak statusu"}
                    </span>
                    <strong>{dziecko.imie} {dziecko.nazwisko}</strong>
                    <small>{dziecko.rodzic || "Brak przypisanego rodzica"}</small>
                  </div>

                  <div className="presence-actions" aria-label={`Status dziecka ${dziecko.imie} ${dziecko.nazwisko}`}>
                    {STATUSY.map((status) => (
                      <button
                        className={dziecko.statusPobytu === status ? "status-button active" : "status-button"}
                        type="button"
                        key={status}
                        disabled={savingId === dziecko.id}
                        onClick={() => ustawStatus(dziecko.id, status)}
                      >
                        {labelStatusu(status)}
                      </button>
                    ))}
                  </div>
                </article>
              ))}
            </div>
          )}
        </section>
      </div>
    </div>
  );
}

function labelStatusu(status) {
  if (status.includes("odebrane")) {
    return "Odebrane";
  }

  if (status.includes("nieobec")) {
    return "Nieobecne";
  }

  if (status.includes("obec")) {
    return "Obecne";
  }

  return "Nieobecne";
}

function statusClass(status = "") {
  const normalized = status.toLowerCase();

  if (normalized.includes("odebrane") || normalized.includes("nieobec")) {
    return "status-pill away";
  }

  if (normalized.includes("obec")) {
    return "status-pill present";
  }

  return "status-pill";
}
