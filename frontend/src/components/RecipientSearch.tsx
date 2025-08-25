import { useEffect, useRef, useState } from "react";
import { searchRecipients, type Recipient } from "../api/recipients";

type Props = {
  onSelect: (r: Recipient) => void;
  label?: string;
};

export default function RecipientSearch({ onSelect, label = "Find Recipient" }: Props) {
  const [query, setQuery] = useState("");
  const [results, setResults] = useState<Recipient[]>([]);
  const [loading, setLoading] = useState(false);
  const [waking, setWaking] = useState(false); // shows "Waking server…" if it’s slow
  const [error, setError] = useState<string | null>(null);
  const abortRef = useRef<AbortController | null>(null);
  const wakeTimer = useRef<number | null>(null);

  useEffect(() => {
    return () => {
      abortRef.current?.abort();
      if (wakeTimer.current) window.clearTimeout(wakeTimer.current);
    };
  }, []);

  const handleSearch = async () => {
    setError(null);
    setResults([]);
    setLoading(true);
    setWaking(false);

    // show a friendly message if backend is cold/slow
    wakeTimer.current = window.setTimeout(() => setWaking(true), 3000);

    abortRef.current?.abort();
    const ac = new AbortController();
    abortRef.current = ac;

    try {
      const data = await searchRecipients(query);
      setResults(data);
    } catch (e: any) {
      if (e.name !== "AbortError") {
        setError(e.message || "Something went wrong.");
      }
    } finally {
      setLoading(false);
      if (wakeTimer.current) window.clearTimeout(wakeTimer.current);
      setWaking(false);
    }
  };

  return (
    <div className="space-y-3">
      <div className="flex gap-2">
        <input
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          placeholder="Search by name or email"
          className="flex-1 border rounded px-3 py-2"
        />
        <button
          onClick={handleSearch}
          className="px-4 py-2 rounded bg-black text-white hover:opacity-90 disabled:opacity-60"
          disabled={loading || !query.trim()}
        >
          {label}
        </button>
      </div>

      {loading && <p className="text-sm text-gray-600">Searching…</p>}
      {waking && <p className="text-sm text-amber-600">Waking server… this can take a bit on free tier.</p>}
      {error && <p className="text-sm text-red-600">{error}</p>}

      {!loading && !error && results.length > 0 && (
        <ul className="border rounded divide-y">
          {results.map((r) => (
            <li
              key={r.id}
              className="p-3 cursor-pointer hover:bg-gray-50 flex items-center justify-between"
              onClick={() => onSelect(r)}
            >
              <div>
                <div className="font-medium">{r.name}</div>
                <div className="text-sm text-gray-600">{r.email}</div>
              </div>
              <div className="text-sm text-gray-800">•••• {r.cardNumberLast4Digits}</div>
            </li>
          ))}
        </ul>
      )}

      {!loading && !error && results.length === 0 && query.trim() && (
        <p className="text-sm text-gray-500">No matches.</p>
      )}
    </div>
  );
}