import { BASE_URL } from "../config";
import type { ProblemDetail } from "../types/http";

export type TransferRequest = {
  fromAccountId: number;
  toAccountId: number;
  amount: number;
};

export type TransferResponse = {
  fromAccountId: number;
  toAccountId: number;
  amount: string | number;
  fromBalance: string | number;
  toBalance: string | number;
};

export async function createTransfer(
  body: TransferRequest,
  opts: { signal?: AbortSignal } = {}
): Promise<TransferResponse> {
  const res = await fetch(`${BASE_URL}/api/transfers`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(body),
    signal: opts.signal,
  });

  if (!res.ok) {
    // Try to parse ProblemDetail; fall back to text
    let problem: ProblemDetail | undefined;
    let fallbackText: string | undefined;

    try {
      problem = await res.json();
    } catch {
      try {
        fallbackText = await res.text();
      } catch {
        // ignore
      }
    }

    const message =
      problem?.detail ||
      problem?.title ||
      fallbackText ||
      `Transfer failed (${res.status} ${res.statusText})`;

    const error = new Error(message) as Error & {
      status?: number;
      code?: string;
      body?: unknown;
    };
    error.status = res.status;
    if (problem?.code) error.code = problem.code;
    error.body = problem ?? fallbackText;
    throw error;
  }

  return (await res.json()) as TransferResponse;
}