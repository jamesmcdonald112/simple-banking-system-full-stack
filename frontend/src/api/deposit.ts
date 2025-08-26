// src/api/deposit.ts
import { BASE_URL } from "../config";
import { toast } from "react-hot-toast";

export async function deposit(accountId: number, amount: number) {
  const res = await fetch(`${BASE_URL}/api/accounts/${accountId}/deposit`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ amount }),
  });

  if (!res.ok) {
    const text = await res.text().catch(() => "");
    toast.error("Deposit failed");
    throw new Error(`Deposit failed (${res.status} ${res.statusText}) ${text}`);
  }

  const data = await res.json();
  toast.success(`Deposit of €${amount} successful`);
  return data;
}