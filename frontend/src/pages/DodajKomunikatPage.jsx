import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../App.css";

export default function DodajKomunikatPage() {
  const [tytul, setTytul] = useState("");
  const [tresc, setTresc] = useState("");
  const [opublikowany, setOpublikowany] = useState(true);
  const [komunikat, setKomunikat] = useState("");
  const [blad, setBlad] = useState("");

  const navigate = useNavigate();

  async function dodajKomunikat(e) {
    e.preventDefault();

    setKomunikat("");
    setBlad("");

    const token = localStorage.getItem("token");

    if (!token) {
      setBlad("Brak tokena. Najpierw się zaloguj.");
      return;
    }

    try {
      const response = await fetch("http://localhost:8080/api/admin/posts", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`
        },
        body: JSON.stringify({
          tytul: tytul,
          tresc: tresc,
          opublikowany: opublikowany
        })
      });

      if (!response.ok) {
        throw new Error("Nie udało się dodać komunikatu.");
      }

      setKomunikat("Komunikat został dodany.");
      setTytul("");
      setTresc("");
      setOpublikowany(true);
    } catch (error) {
      setBlad(error.message);
    }
  }

  function wroc() {
    navigate("/komunikaty");
  }

  return (
    <div className="shell">
      <div className="card" style={{ maxWidth: 700, margin: "40px auto", width: "100%" }}>
        <h3>Dodaj komunikat</h3>

        <form className="form" onSubmit={dodajKomunikat}>
          <div className="field full">
            <label>Tytuł</label>
            <input
              value={tytul}
              onChange={(e) => setTytul(e.target.value)}
              placeholder="Np. Zebranie z rodzicami"
            />
          </div>

          <div className="field full">
            <label>Treść</label>
            <textarea
              value={tresc}
              onChange={(e) => setTresc(e.target.value)}
              placeholder="Wpisz treść komunikatu"
              rows="5"
              style={{
                border: "1px solid var(--border)",
                borderRadius: 12,
                padding: 10,
                fontSize: 14,
                fontFamily: "inherit",
                resize: "vertical"
              }}
            />
          </div>

          <div className="field full">
            <label>
              <input
                type="checkbox"
                checked={opublikowany}
                onChange={(e) => setOpublikowany(e.target.checked)}
                style={{ marginRight: 8 }}
              />
              Opublikowany
            </label>
          </div>

          <div className="full" style={{ display: "flex", gap: 10 }}>
            <button className="btn" type="submit">
              Dodaj komunikat
            </button>

            <button className="btn" type="button" onClick={wroc}>
              Wróć
            </button>
          </div>
        </form>

        {komunikat && (
          <div className="empty" style={{ marginTop: 12, color: "green" }}>
            {komunikat}
          </div>
        )}

        {blad && (
          <div className="error" style={{ marginTop: 12 }}>
            {blad}
          </div>
        )}
      </div>
    </div>
  );
}
