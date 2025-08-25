import { useState } from "react";
import type { Recipient } from "../api/recipients";
import { searchRecipients } from "../api/recipients";

type Props = {
  open: boolean;
  onClose: () => void;
};

export default function TransferModal({ open, onClose }: Props) {
  const [query, setQuery] = useState("");
  const [results, setResults] = useState<Recipient[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const [selected, setSelected] = useState<Recipient | null>(null);
  const [amount, setAmount] = useState("");

  if (!open) return null;

  async function handleSearch() {
    setError(null);
    setLoading(true);
    setResults([]);
    try {
      const data = await searchRecipients(query);
      setResults(data);
    } catch (e: any) {
      setError(e?.message ?? "Search failed");
    } finally {
      setLoading(false);
    }
  }

  function handleSend() {
    // TODO: replace with POST /api/transfers
    console.log("Send", { to: selected, amount: Number(amount) });
    onClose();
  }

  return (
    <div className="fixed inset-0 z-50 grid place-items-center bg-black/40 px-4">
      <div className="w-full max-w-lg rounded-lg bg-white p-4 shadow-lg">
        <div className="flex items-center justify-between mb-2">
          <h3 className="text-lg font-semibold">Transfer</h3>
          <button onClick={onClose} className="text-sm text-gray-500 hover:text-black">Close</button>
        </div>

        {!selected && (
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
                Find
              </button>
            </div>

            {loading && <p className="text-sm text-gray-600">Searching…</p>}
            {error && <p className="text-sm text-red-600">{error}</p>}

            {!loading && !error && results.length > 0 && (
              <ul className="border rounded divide-y max-h-60 overflow-auto">
                {results.map((r) => (
                  <li
                    key={r.id}
                    className="p-3 cursor-pointer hover:bg-gray-50 flex items-center justify-between"
                    onClick={() => setSelected(r)}
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
        )}

        {selected && (
          <div className="space-y-4">
            <div className="p-3 border rounded">
              <div className="font-medium">{selected.name}</div>
              <div className="text-sm text-gray-600">{selected.email}</div>
              <div className="text-sm">Card •••• {selected.cardNumberLast4Digits}</div>
            </div>

            <div className="flex gap-2 items-center">
              <input
                type="number"
                min="0"
                step="0.01"
                value={amount}
                onChange={(e) => setAmount(e.target.value)}
                placeholder="Amount"
                className="flex-1 border rounded px-3 py-2"
              />
              <button
                className="px-4 py-2 rounded bg-black text-white hover:opacity-90 disabled:opacity-60"
                disabled={!amount || Number(amount) <= 0}
                onClick={handleSend}
              >
                Send
              </button>
            </div>

            <button className="text-sm underline text-gray-600" onClick={() => setSelected(null)}>
              Change recipient
            </button>
          </div>
        )}
      </div>
    </div>
  );
}