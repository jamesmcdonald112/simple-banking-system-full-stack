import { BASE_URL } from "../config";
import type { ProblemDetail } from "../types/http";

export async function deposit(accountId: number, amount: number) {
  const res = await fetch(`${BASE_URL}/api/accounts/${accountId}/deposit`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ amount }),
  });

  if (!res.ok) {
    let problem: ProblemDetail | undefined;
    try {
      problem = (await res.json()) as ProblemDetail;
    } catch {
      // ignore parse errors. fall back to status text
    }

    const message =
      problem?.detail ||
      problem?.title ||
      `Deposit failed (${res.status} ${res.statusText})`;

    const error = new Error(message) as Error & { status?: number; code?: string };
    error.status = res.status;
    // Some of our problem payloads may include a custom code
    if ((problem as any)?.code) error.code = (problem as any).code;

    throw error;
  }

  return res.json();
}