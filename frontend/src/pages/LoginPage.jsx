import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function LoginPage() {
  const [email, setEmail] = useState("");
  const [haslo, setHaslo] = useState("");
  const [blad, setBlad] = useState("");
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();

  async function zaloguj(e) {
    e.preventDefault();
    setBlad("");
    setLoading(true);

    try {
      const response = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ email, haslo })
      });

      if (!response.ok) {
        const message = await response.text();
        setBlad(message || "Nieprawidlowy email lub haslo.");
        return;
      }

      const data = await response.json();
      localStorage.setItem("token", data.token);
      navigate("/panel");
    } catch {
      setBlad("Nie udalo sie polaczyc z serwerem. Sprawdz, czy backend dziala na porcie 8080.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <main className="login-page">
      <section className="login-hero">
        <div className="login-copy">
          <div className="login-kicker">System przedszkola</div>
          <h1>Panel opieki i komunikatow przedszkola</h1>
          <p>
            Zaloguj sie do systemu, aby przejsc do panelu rodzica, nauczyciela albo administracji.
            Uprawnienia zostana dopasowane automatycznie do Twojego konta.
          </p>
        </div>

        <form className="login-card" onSubmit={zaloguj}>
          <div className="login-card-head">
            <span className="brand-mark">P</span>
            <div>
              <h2>Logowanie</h2>
              <p>Wpisz email i haslo do konta.</p>
            </div>
          </div>

          <label className="login-field">
            Email
            <input value={email} onChange={(e) => setEmail(e.target.value)} />
          </label>

          <label className="login-field">
            Haslo
            <input type="password" value={haslo} onChange={(e) => setHaslo(e.target.value)} />
          </label>

          <button className="login-submit" disabled={loading} type="submit">
            {loading ? "Logowanie..." : "Zaloguj"}
          </button>

          {blad && <div className="error">{blad}</div>}
        </form>
      </section>
    </main>
  );
}
