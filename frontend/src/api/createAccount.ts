import { BASE_URL } from "../config";
import type { Account } from "../types/Account";

export async function createAccount(): Promise<Account> {
  const res = await fetch(`${BASE_URL}/api/accounts`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({})
  });

  if (!res.ok) {
    throw new Error("Failed to create account");
  }

  return res.json();
}