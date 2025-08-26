import { useState } from "react";
import { toast } from "react-hot-toast";
import type { Recipient } from "../api/recipients";
import { searchRecipients } from "../api/recipients";
import { createTransfer, type TransferResponse } from "../api/transfers";

type Props = {
  open: boolean;
  onClose: () => void;
  fromAccountId: number;                 
  onSuccess: (r: TransferResponse) => void; 
};

export default function TransferModal({ open, onClose, fromAccountId, onSuccess }: Props) {
  const [query, setQuery] = useState("");
  const [results, setResults] = useState<Recipient[]>([]);
  const [loading, setLoading] = useState(false);
  const [sending, setSending] = useState(false);
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
    } catch (e) {
      const message = e instanceof Error ? e.message : "Search failed";
      setError(message);
      toast.error(message);
    } finally {
      setLoading(false);
    }
  }

  async function handleSend() {
    if (!selected) return;
    const value = Number(amount);
    if (!value || value <= 0) {
      setError("Please enter a valid amount");
      return;
    }
    setError(null);
    setSending(true);
    try {
      const result = await createTransfer({
        fromAccountId,
        toAccountId: selected.id,
        amount: value,
      });
      onSuccess(result);
      toast.success(`Sent €${Number(value).toFixed(2)} to ${selected?.name}`);
      onClose();
    } catch (e) {
      const message = e instanceof Error ? e.message : "Transfer failed";
      setError(message);
      toast.error(message);
    } finally {
      setSending(false);
    }
  }

  return (
    <div className="fixed inset-0 z-50 grid place-items-center bg-black/40 px-4">
      <div className="w-full max-w-lg rounded-lg bg-white p-4 shadow-lg">
        <div className="flex items-center justify-between mb-2">
          <h3 className="text-lg font-semibold">Transfer</h3>
          <button onClick={onClose} className="text-sm text-gray-500 hover:text-black">Close</button>
        </div>

        {error && <p className="mb-2 text-sm text-red-600">{error}</p>}

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
                {loading ? "…" : "Find"}
              </button>
            </div>

            {!loading && results.length > 0 && (
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

            {!loading && results.length === 0 && query.trim() && (
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
                disabled={sending || !amount || Number(amount) <= 0}
                onClick={handleSend}
              >
                {sending ? "Sending…" : "Send"}
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