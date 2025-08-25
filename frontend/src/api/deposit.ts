// src/api/deposit.ts
import { BASE_URL } from "../config";

export async function deposit(accountId: number, amount: number) {
  const res = await fetch(`${BASE_URL}/api/accounts/${accountId}/deposit`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ amount }),
  });

  if (!res.ok) {
    const text = await res.text().catch(() => "");
    throw new Error(`Deposit failed (${res.status} ${res.statusText}) ${text}`);
  }

  return res.json();
}