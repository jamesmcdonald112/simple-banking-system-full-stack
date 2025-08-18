

import { useState } from "react";
import Button from "./Button";
import type { Account } from "../types/Account";
import { deposit } from "../api/deposit";

type Props = {
  open: boolean;
  onClose: () => void;
  account: Account;
  onDeposited: (newBalance: number, updated?: Partial<Account>) => void;
};

export default function AddMoneyModal({ open, onClose, account, onDeposited }: Props) {
  const [amount, setAmount] = useState<string>("");
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState<string>("");

  if (!open) return null;

  function validate(raw: string) {
    if (!raw) return "Enter an amount";
    // Only numbers with up to 2 decimal places
    if (!/^\d+(\.\d{1,2})?$/.test(raw)) return "Use a valid amount (max 2 decimals)";
    const n = Number(raw);
    if (!(n > 0)) return "Amount must be greater than 0";
    if (n > 100000) return "Amount too large";
    return "";
  }

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    const v = validate(amount);
    if (v) {
      setError(v);
      return;
    }
    setSubmitting(true);
    setError("");

    try {
      const result = await deposit(account.id, Number(amount));
      // prefer returned balance if present, else fall back
      const newBalance =
        typeof (result as any)?.balance === "number"
          ? (result as any).balance
          : account.balance + Number(amount);

      onDeposited(newBalance, result as Partial<Account>);
      onClose();
      setAmount("");
    } catch (err) {
      const e2 = err as Error & { status?: number };
      setError(e2.message || "Deposit failed");
    } finally {
      setSubmitting(false);
    }
  }

  function handleBackdropClick(e: React.MouseEvent<HTMLDivElement>) {
    if (e.target === e.currentTarget) onClose();
  }

  return (
    <div
      className="fixed inset-0 z-50 flex items-center justify-center bg-black/60"
      role="dialog"
      aria-modal="true"
      aria-labelledby="add-money-title"
      onClick={handleBackdropClick}
    >
      <form
        onSubmit={handleSubmit}
        className="w-full max-w-sm rounded-xl bg-[#1E1E2D] text-white p-6 shadow-xl"
      >
        <div className="flex items-start justify-between">
          <h3 id="add-money-title" className="text-lg font-semibold">
            Add money
          </h3>
          <button
            type="button"
            onClick={onClose}
            className="text-white/70 hover:text-white transition-colors"
            aria-label="Close"
          >
            ✕
          </button>
        </div>

        <label className="block text-sm text-gray-300 mt-4 mb-2" htmlFor="amount">
          Amount (EUR)
        </label>
        <input
          id="amount"
          inputMode="decimal"
          className="w-full rounded-md bg-[#25253D] border border-[#3B3B54] px-3 py-2 outline-none focus:ring-2 focus:ring-indigo-500"
          placeholder="100.00"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
          autoFocus
        />

        {error && <p className="mt-2 text-sm text-red-400">{error}</p>}

        <div className="mt-6 flex justify-end gap-2">
          <Button type="button" onClick={onClose} disabled={submitting}>
            Cancel
          </Button>
          <Button type="submit" disabled={submitting}>
            {submitting ? "Adding..." : "Add money"}
          </Button>
        </div>
      </form>
    </div>
  );
}