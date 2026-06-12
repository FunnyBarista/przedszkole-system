import { useEffect, useState } from "react";
import { getPublicPosts } from "../api/posts";

export default function NewsPage() {
  const [posts, setPosts] = useState([]);
  const [blad, setBlad] = useState(null);
  const [ladowanie, setLadowanie] = useState(true);

  useEffect(() => {
    (async () => {
      try {
        setLadowanie(true);
        setBlad(null);
        const data = await getPublicPosts();
        setPosts(data || []);
      } catch (e) {
        setBlad(e.message);
      } finally {
        setLadowanie(false);
      }
    })();
  }, []);

  return (
    <div style={{ maxWidth: 900, margin: "40px auto", padding: 16 }}>
      <h1>Aktualności</h1>

      {ladowanie && <p>Ładowanie...</p>}
      {blad && <p style={{ color: "crimson" }}>Błąd: {blad}</p>}

      {!ladowanie && !blad && posts.length === 0 && <p>Brak aktualności.</p>}

      {posts.map((p) => (
        <article key={p.idPost} style={{ border: "1px solid #ddd", padding: 16, marginTop: 12 }}>
          <h3 style={{ marginTop: 0 }}>{p.tytul}</h3>
          <p style={{ whiteSpace: "pre-wrap" }}>{p.tresc}</p>
          {p.utworzono && (
            <small style={{ color: "#666" }}>
              {new Date(p.utworzono).toLocaleString()}
            </small>
          )}
        </article>
      ))}
    </div>
  );
}
