import { useEffect, useState } from "react";
import "../App.css";

function getSession() {
  const token = localStorage.getItem("token");

  if (!token) {
    return null;
  }

  try {
    const payload = JSON.parse(atob(token.split(".")[1]));
    return {
      token,
      rola: payload.rola
    };
  } catch {
    return null;
  }
}

export default function KomunikatyPage() {
  const [posty, setPosty] = useState([]);
  const [blad, setBlad] = useState("");
  const [komunikat, setKomunikat] = useState("");
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [tytul, setTytul] = useState("");
  const [tresc, setTresc] = useState("");

  const session = getSession();
  const mozeDodawac = session && ["ADMIN", "DYREKCJA"].includes(session.rola);

  async function pobierzKomunikaty() {
    setLoading(true);
    setBlad("");

    try {
      const response = await fetch("http://localhost:8080/api/posts");

      if (!response.ok) {
        throw new Error("Nie udalo sie pobrac komunikatow.");
      }

      const data = await response.json();
      setPosty(data);
    } catch (error) {
      setBlad(error.message);
      setPosty([]);
    } finally {
      setLoading(false);
    }
  }

  async function dodajKomunikat(e) {
    e.preventDefault();
    setBlad("");
    setKomunikat("");

    if (!tytul.trim() || !tresc.trim()) {
      setBlad("Tytul i tresc komunikatu sa wymagane.");
      return;
    }

    if (!session?.token) {
      setBlad("Najpierw sie zaloguj.");
      return;
    }

    setSaving(true);

    try {
      const response = await fetch("http://localhost:8080/api/admin/posts", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${session.token}`
        },
        body: JSON.stringify({
          tytul,
          tresc,
          opublikowany: true
        })
      });

      if (!response.ok) {
        throw new Error("Nie udalo sie dodac komunikatu.");
      }

      setTytul("");
      setTresc("");
      setKomunikat("Komunikat zostal dodany.");
      await pobierzKomunikaty();
    } catch (error) {
      setBlad(error.message);
    } finally {
      setSaving(false);
    }
  }

  useEffect(() => {
    pobierzKomunikaty();
  }, []);

  return (
    <div className="shell">
      <div className="content-grid">
        <section className="card">
          <div className="section-head">
            <div>
              <h3>Komunikaty</h3>
              <div className="empty">Aktualnosci i ogloszenia przedszkola.</div>
            </div>
          </div>

          {loading && (
            <div className="empty" style={{ marginTop: 14 }}>
              Ladowanie komunikatow...
            </div>
          )}

          {!loading && posty.length === 0 && (
            <div className="empty" style={{ marginTop: 14 }}>
              Brak komunikatow do wyswietlenia.
            </div>
          )}

          {!loading && posty.length > 0 && (
            <div className="post-list">
              {posty.map((post, index) => (
                <article className="post-item" key={post.id ?? post.id_postu ?? index}>
                  <h4>{post.tytul}</h4>
                  <p>{post.tresc}</p>
                </article>
              ))}
            </div>
          )}
        </section>

        {mozeDodawac && (
          <section className="card">
            <h3>Dodaj komunikat</h3>
            <form className="form single" onSubmit={dodajKomunikat}>
              <div className="field full">
                <label>Tytul</label>
                <input
                  value={tytul}
                  onChange={(e) => setTytul(e.target.value)}
                  placeholder="Np. Zebranie z rodzicami"
                />
              </div>

              <div className="field full">
                <label>Tresc</label>
                <textarea
                  value={tresc}
                  onChange={(e) => setTresc(e.target.value)}
                  placeholder="Wpisz tresc komunikatu"
                  rows="6"
                />
              </div>

              <button className="btn full" disabled={saving} type="submit">
                {saving ? "Dodawanie..." : "Dodaj komunikat"}
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
