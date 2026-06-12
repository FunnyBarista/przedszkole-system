import { BrowserRouter, Routes, Route, Link, Navigate, useLocation } from "react-router-dom";
import MojeDzieciPage from "./pages/MojeDzieciPage";
import LoginPage from "./pages/LoginPage";
import KomunikatyPage from "./pages/KomunikatyPage";
import JadlospisPage from "./pages/JadlospisPage";
import OsobyPage from "./pages/OsobyPage";
import ObecnoscPage from "./pages/ObecnoscPage";
import "./App.css";

function getSession() {
  const token = localStorage.getItem("token");

  if (!token) {
    return null;
  }

  try {
    const payload = JSON.parse(atob(token.split(".")[1]));
    return {
      token,
      email: payload.sub,
      rola: payload.rola
    };
  } catch {
    return null;
  }
}

function RequireAuth({ children }) {
  if (!getSession()) {
    return <Navigate to="/" replace />;
  }

  return children;
}

function RequireAdmin({ children }) {
  const session = getSession();

  if (!session || !["ADMIN", "DYREKCJA"].includes(session.rola)) {
    return <Navigate to="/komunikaty" replace />;
  }

  return children;
}

function RequireStaff({ children }) {
  const session = getSession();

  if (!session || !["NAUCZYCIEL", "DYREKCJA", "ADMIN"].includes(session.rola)) {
    return <Navigate to="/panel" replace />;
  }

  return children;
}

function AppShell({ children }) {
  const location = useLocation();
  const session = getSession();

  if (location.pathname === "/") {
    return children;
  }

  return (
    <div className="app-frame">
      <aside className="app-sidebar">
        <Link className="brand-block" to="/panel">
          <span className="brand-mark">P</span>
          <span>
            <strong>Przedszkole</strong>
            <small>panel systemu</small>
          </span>
        </Link>

        <nav className="side-nav">
          <Link to="/panel">Moje dzieci</Link>
          {session && ["NAUCZYCIEL", "DYREKCJA", "ADMIN"].includes(session.rola) && (
            <Link to="/obecnosc">Obecnosc</Link>
          )}
          <Link to="/komunikaty">Komunikaty</Link>
          <Link to="/jadlospis">Jadlospis</Link>
          {session && ["ADMIN", "DYREKCJA"].includes(session.rola) && (
            <Link to="/osoby">Osoby i dzieci</Link>
          )}
        </nav>

        {session && (
          <div className="session-box">
            <span>{session.email}</span>
            <strong>{session.rola}</strong>
          </div>
        )}
      </aside>

      <main className="app-content">{children}</main>
    </div>
  );
}

export default function App() {
  return (
    <BrowserRouter>
      <AppShell>
        <Routes>
          <Route path="/" element={<LoginPage />} />
          <Route
            path="/panel"
            element={
              <RequireAuth>
                <MojeDzieciPage />
              </RequireAuth>
            }
          />
          <Route path="/moje-dzieci" element={<Navigate to="/panel" replace />} />
          <Route path="/login" element={<Navigate to="/" replace />} />
          <Route path="/komunikaty" element={<KomunikatyPage />} />
          <Route path="/jadlospis" element={<JadlospisPage />} />
          <Route
            path="/obecnosc"
            element={
              <RequireStaff>
                <ObecnoscPage />
              </RequireStaff>
            }
          />
          <Route
            path="/osoby"
            element={
              <RequireAdmin>
                <OsobyPage />
              </RequireAdmin>
            }
          />
          <Route path="/dodaj-komunikat" element={<Navigate to="/komunikaty" replace />} />
        </Routes>
      </AppShell>
    </BrowserRouter>
  );
}
