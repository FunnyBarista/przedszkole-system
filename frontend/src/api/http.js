export async function http(url, options = {}) {
  const res = await fetch(url, {
    headers: {
      "Content-Type": "application/json",
      ...(options.headers || {}),
    },
    ...options,
  });

  // Spróbuj odczytać JSON albo tekst błędu
  const contentType = res.headers.get("content-type") || "";
  const isJson = contentType.includes("application/json");
  const data = isJson ? await res.json().catch(() => null) : await res.text().catch(() => null);

  if (!res.ok) {
    const message =
      (data && data.message) ||
      (data && data.error) ||
      (typeof data === "string" ? data : null) ||
      `HTTP ${res.status}`;
    throw new Error(message);
  }

  return data;
}
