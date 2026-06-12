import { useEffect, useState } from "react";
import "../App.css";

function getSession() {
  const token = localStorage.getItem("token");

  if (!token) {
    return null;
  }

  try {
    const payload = JSON.parse(atob(token.split(".")[1]));
    return { token, rola: payload.rola };
  } catch {
    return null;
  }
}

export default function JadlospisPage() {
  const [posilki, setPosilki] = useState([]);
  const [data, setData] = useState("");
  const [sniadanie, setSniadanie] = useState("");
  const [obiad, setObiad] = useState("");
  const [blad, setBlad] = useState("");
  const [komunikat, setKomunikat] = useState("");
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);

  const session = getSession();
  const mozeDodawac = session && ["NAUCZYCIEL", "DYREKCJA", "ADMIN"].includes(session.rola);

  async function pobierzJadlospis() {
    setLoading(true);
    setBlad("");

    try {
      const response = await fetch("http://localhost:8080/api/posilki");

      if (!response.ok) {
        throw new Error("Nie udalo sie pobrac jadlospisu.");
      }

      const data = await response.json();
      setPosilki(Array.isArray(data) ? data : []);
    } catch (error) {
      setBlad(error.message);
      setPosilki([]);
    } finally {
      setLoading(false);
    }
  }

  async function dodajPosilek(e) {
    e.preventDefault();
    setBlad("");
    setKomunikat("");

    if (!data || !sniadanie.trim() || !obiad.trim()) {
      setBlad("Data, sniadanie i obiad sa wymagane.");
      return;
    }

    setSaving(true);

    try {
      const response = await fetch("http://localhost:8080/api/posilki", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${session.token}`
        },
        body: JSON.stringify({ data, sniadanie, obiad })
      });

      if (!response.ok) {
        throw new Error("Nie udalo sie dodac jadlospisu.");
      }

      setData("");
      setSniadanie("");
      setObiad("");
      setKomunikat("Jadlospis zostal dodany.");
      await pobierzJadlospis();
    } catch (error) {
      setBlad(error.message);
    } finally {
      setSaving(false);
    }
  }

  useEffect(() => {
    pobierzJadlospis();
  }, []);

  return (
    <div className="shell">
      <div className="content-grid">
        <section className="card">
          <h3>Jadlospis</h3>
          <div className="empty">Plan posilkow widoczny dla wszystkich uzytkownikow.</div>

          {loading && <div className="empty" style={{ marginTop: 14 }}>Ladowanie jadlospisu...</div>}

          {!loading && posilki.length === 0 && (
            <div className="empty" style={{ marginTop: 14 }}>Brak wpisow jadlospisu.</div>
          )}

          {!loading && posilki.length > 0 && (
            <div className="meal-grid">
              {posilki.map((posilek) => (
                <article className="meal-card" key={posilek.jadlospis_id}>
                  <span>{posilek.data}</span>
                  <h4>Sniadanie</h4>
                  <p>{posilek.sniadanie}</p>
                  <h4>Obiad</h4>
                  <p>{posilek.obiad}</p>
                </article>
              ))}
            </div>
          )}
        </section>

        {mozeDodawac && (
          <section className="card">
            <h3>Dodaj jadlospis</h3>
            <form className="form single" onSubmit={dodajPosilek}>
              <div className="field full">
                <label>Data</label>
                <input type="date" value={data} onChange={(e) => setData(e.target.value)} />
              </div>

              <div className="field full">
                <label>Sniadanie</label>
                <input value={sniadanie} onChange={(e) => setSniadanie(e.target.value)} />
              </div>

              <div className="field full">
                <label>Obiad</label>
                <input value={obiad} onChange={(e) => setObiad(e.target.value)} />
              </div>

              <button className="btn full" disabled={saving} type="submit">
                {saving ? "Dodawanie..." : "Dodaj jadlospis"}
              </button>
            </form>
          </section>
        )}

        {blad && <div className="error">{blad}</div>}
        {komunikat && <div className="success">{komunikat}</div>}
      </div>
    </div>
  );
}
