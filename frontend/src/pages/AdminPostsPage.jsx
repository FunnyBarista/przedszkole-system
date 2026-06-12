import { useEffect, useMemo, useState } from "react";
import { createPost, deletePost, getAdminPosts, updatePost } from "../api/posts";

export default function AdminPostsPage() {
  const [posts, setPosts] = useState([]);
  const [blad, setBlad] = useState(null);
  const [ladowanie, setLadowanie] = useState(true);

  const [tytul, setTytul] = useState("");
  const [tresc, setTresc] = useState("");
  const [opublikowany, setOpublikowany] = useState(false);

  const [edycjaId, setEdycjaId] = useState(null);

  const edytowany = useMemo(
    () => posts.find((x) => x.idPost === edycjaId) || null,
    [posts, edycjaId]
  );

  async function odswiez() {
    try {
      setLadowanie(true);
      setBlad(null);
      const data = await getAdminPosts();
      setPosts(Array.isArray(data) ? data : []);
    } catch (e) {
      setBlad(e.message);
    } finally {
      setLadowanie(false);
    }
  }

  useEffect(() => {
    odswiez();
  }, []);

  function zaladujDoFormularza(p) {
    setEdycjaId(p.idPost);
    setTytul(p.tytul || "");
    setTresc(p.tresc || "");
    setOpublikowany(!!p.opublikowany);
  }

  function resetForm() {
    setEdycjaId(null);
    setTytul("");
    setTresc("");
    setOpublikowany(false);
  }

  async function zapisz(e) {
    e.preventDefault();
    setBlad(null);

    if (!tytul.trim() || !tresc.trim()) {
      setBlad("Tytuł i treść są wymagane.");
      return;
    }

    try {
      const payload = { tytul, tresc, opublikowany };

      if (edycjaId) {
        await updatePost(edycjaId, payload);
      } else {
        await createPost(payload);
      }

      resetForm();
      await odswiez();
    } catch (err) {
      setBlad(err.message);
    }
  }

  async function usun(id) {
    if (!confirm("Na pewno usunąć post?")) return;
    setBlad(null);
    try {
      await deletePost(id);
      await odswiez();
    } catch (e) {
      setBlad(e.message);
    }
  }

  return (
    <div style={{ maxWidth: 1000, margin: "40px auto", padding: 16 }}>
      <h1>Admin: Aktualności</h1>

      {blad && <p style={{ color: "crimson" }}>Błąd: {blad}</p>}

      <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: 16 }}>
        <form onSubmit={zapisz} style={{ border: "1px solid #ddd", padding: 16 }}>
          <h3 style={{ marginTop: 0 }}>{edycjaId ? "Edycja posta" : "Nowy post"}</h3>

          <div style={{ marginBottom: 10 }}>
            <label>Tytuł</label>
            <input
              value={tytul}
              onChange={(e) => setTytul(e.target.value)}
              style={{ width: "100%", padding: 8 }}
              placeholder="Np. Zebranie rodziców"
            />
          </div>

          <div style={{ marginBottom: 10 }}>
            <label>Treść</label>
            <textarea
              value={tresc}
              onChange={(e) => setTresc(e.target.value)}
              style={{ width: "100%", padding: 8, minHeight: 160 }}
              placeholder="Treść aktualności..."
            />
          </div>

          <label style={{ display: "flex", gap: 8, alignItems: "center" }}>
            <input
              type="checkbox"
              checked={opublikowany}
              onChange={(e) => setOpublikowany(e.target.checked)}
            />
            Opublikowany
          </label>

          <div style={{ display: "flex", gap: 8, marginTop: 12 }}>
            <button type="submit">{edycjaId ? "Zapisz zmiany" : "Dodaj"}</button>
            {edycjaId && (
              <button type="button" onClick={resetForm}>
                Anuluj
              </button>
            )}
          </div>

          {edycjaId && edytowany && (
            <p style={{ marginTop: 10, color: "#666" }}>
              Edytujesz: <b>{edytowany.tytul}</b>
            </p>
          )}
        </form>

        <div style={{ border: "1px solid #ddd", padding: 16 }}>
          <h3 style={{ marginTop: 0 }}>Lista postów</h3>

          {ladowanie && <p>Ładowanie...</p>}

          {!ladowanie && posts.length === 0 && <p>Brak postów.</p>}

          {posts.map((p) => (
            <div key={p.idPost} style={{ borderTop: "1px solid #eee", paddingTop: 10, marginTop: 10 }}>
              <div style={{ display: "flex", justifyContent: "space-between", gap: 10 }}>
                <div>
                  <b>{p.tytul}</b>{" "}
                  <span style={{ color: p.opublikowany ? "green" : "#666" }}>
                    {p.opublikowany ? "OPUBLIKOWANY" : "SZKIC"}
                  </span>
                  <div style={{ color: "#666", fontSize: 12 }}>
                    {p.utworzono ? new Date(p.utworzono).toLocaleString() : ""}
                  </div>
                </div>

                <div style={{ display: "flex", gap: 8 }}>
                  <button onClick={() => zaladujDoFormularza(p)}>Edytuj</button>
                  <button onClick={() => usun(p.idPost)}>Usuń</button>
                </div>
              </div>

              <div style={{ marginTop: 6, whiteSpace: "pre-wrap" }}>{p.tresc}</div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
