import { useEffect, useState } from "react";
import "../App.css";

export default function OsobyPage() {
  const [rodzice, setRodzice] = useState([]);
  const [dzieci, setDzieci] = useState([]);
  const [blad, setBlad] = useState("");
  const [sukces, setSukces] = useState("");
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [nowyRodzic, setNowyRodzic] = useState({
    imie: "",
    nazwisko: "",
    email: "",
    telefon: "",
    haslo: ""
  });

  async function pobierzDane() {
    setLoading(true);
    setBlad("");

    const token = localStorage.getItem("token");

    try {
      const [rodziceResponse, dzieciResponse] = await Promise.all([
        fetch("http://localhost:8080/api/rodzice", {
          headers: { Authorization: `Bearer ${token}` }
        }),
        fetch("http://localhost:8080/api/admin/dzieci/podglad", {
          headers: { Authorization: `Bearer ${token}` }
        })
      ]);

      if (!rodziceResponse.ok || !dzieciResponse.ok) {
        throw new Error("Nie udalo sie pobrac danych osob. Ten widok wymaga roli ADMIN albo DYREKCJA.");
      }

      setRodzice(await rodziceResponse.json());
      setDzieci(await dzieciResponse.json());
    } catch (error) {
      setBlad(error.message);
      setRodzice([]);
      setDzieci([]);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    pobierzDane();
  }, []);

  function ustawPole(event) {
    const { name, value } = event.target;
    setNowyRodzic((formularz) => ({
      ...formularz,
      [name]: value
    }));
  }

  async function dodajRodzica(event) {
    event.preventDefault();
    setBlad("");
    setSukces("");
    setSaving(true);

    const token = localStorage.getItem("token");

    try {
      const response = await fetch("http://localhost:8080/api/rodzice", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`
        },
        body: JSON.stringify({
          ...nowyRodzic,
          rola: "RODZIC"
        })
      });

      if (!response.ok) {
        const message = await response.text();
        throw new Error(message || "Nie udalo sie utworzyc konta rodzica.");
      }

      setNowyRodzic({
        imie: "",
        nazwisko: "",
        email: "",
        telefon: "",
        haslo: ""
      });
      setSukces("Konto rodzica zostalo utworzone.");
      await pobierzDane();
    } catch (error) {
      setBlad(error.message);
    } finally {
      setSaving(false);
    }
  }

  return (
    <div className="shell">
      <div className="content-grid">
        <section className="stats-grid">
          <div className="stat-card">
            <span>Dorosli</span>
            <strong>{rodzice.length}</strong>
          </div>
          <div className="stat-card">
            <span>Dzieci</span>
            <strong>{dzieci.length}</strong>
          </div>
          <div className="stat-card">
            <span>Nauczyciele</span>
            <strong>{rodzice.filter((r) => r.rola === "NAUCZYCIEL").length}</strong>
          </div>
        </section>

        <section className="card">
          <h3>Dodaj konto rodzica</h3>
          <div className="empty">Administrator moze utworzyc konto rodzica z haslem startowym.</div>

          <form className="form" style={{ marginTop: 14 }} onSubmit={dodajRodzica}>
            <div className="field">
              <label>Imie</label>
              <input name="imie" value={nowyRodzic.imie} onChange={ustawPole} required />
            </div>

            <div className="field">
              <label>Nazwisko</label>
              <input name="nazwisko" value={nowyRodzic.nazwisko} onChange={ustawPole} required />
            </div>

            <div className="field">
              <label>Email</label>
              <input name="email" type="email" value={nowyRodzic.email} onChange={ustawPole} required />
            </div>

            <div className="field">
              <label>Telefon</label>
              <input name="telefon" value={nowyRodzic.telefon} onChange={ustawPole} required />
            </div>

            <div className="field full">
              <label>Haslo startowe</label>
              <input name="haslo" type="password" value={nowyRodzic.haslo} onChange={ustawPole} required />
            </div>

            <div className="full">
              <button className="btn" type="submit" disabled={saving}>
                {saving ? "Tworzenie..." : "Utworz konto rodzica"}
              </button>
            </div>
          </form>

          {sukces && <div className="success" style={{ marginTop: 12 }}>{sukces}</div>}
        </section>

        <section className="card">
          <h3>Osoby dorosle</h3>
          <div className="empty">Rodzice, nauczyciele, dyrekcja i administratorzy w systemie.</div>

          {loading && <div className="empty" style={{ marginTop: 14 }}>Ladowanie danych...</div>}
          {blad && <div className="error" style={{ marginTop: 12 }}>{blad}</div>}

          {!loading && !blad && (
            <div className="table-wrap" style={{ marginTop: 14 }}>
              <table>
                <thead>
                  <tr>
                    <th>Imie</th>
                    <th>Nazwisko</th>
                    <th>Email</th>
                    <th>Telefon</th>
                    <th>Rola</th>
                  </tr>
                </thead>
                <tbody>
                  {rodzice.map((osoba) => (
                    <tr key={osoba.id_rodzica}>
                      <td data-label="Imie">{osoba.imie}</td>
                      <td data-label="Nazwisko">{osoba.nazwisko}</td>
                      <td data-label="Email">{osoba.email}</td>
                      <td data-label="Telefon">{osoba.telefon}</td>
                      <td data-label="Rola">{osoba.rola}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </section>

        <section className="card">
          <h3>Dzieci</h3>
          <div className="empty">Lista dzieci dodanych do systemu.</div>

          {!loading && !blad && (
            <div className="table-wrap" style={{ marginTop: 14 }}>
              <table>
                <thead>
                  <tr>
                    <th>Imie</th>
                    <th>Nazwisko</th>
                    <th>Rodzic/opiekun</th>
                    <th>Email rodzica</th>
                    <th>Status</th>
                  </tr>
                </thead>
                <tbody>
                  {dzieci.map((dziecko) => (
                    <tr key={dziecko.id}>
                      <td data-label="Imie">{dziecko.imie}</td>
                      <td data-label="Nazwisko">{dziecko.nazwisko}</td>
                      <td data-label="Rodzic/opiekun">{dziecko.rodzic || "Brak przypisania"}</td>
                      <td data-label="Email rodzica">{dziecko.emailRodzica || "-"}</td>
                      <td data-label="Status">{dziecko.statusPobytu}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </section>
      </div>
    </div>
  );
}
