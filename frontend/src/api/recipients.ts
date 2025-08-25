// src/api/recipients.ts
import { BASE_URL } from "../config";
import type { ProblemDetail } from "../types/http";

export type Recipient = {
  id: number;
  name: string;
  email: string;
  cardNumberLast4Digits: string;
};

export async function searchRecipients(query: string): Promise<Recipient[]> {
  if (!query.trim()) return [];

  const res = await fetch(
    `${BASE_URL}/api/accounts/recipients?query=${encodeURIComponent(query)}`
  );

  if (!res.ok) {
    let problem: ProblemDetail | undefined;
    try {
      problem = await res.json();
    } catch {
      // ignore parse errors
    }
    const message = problem?.detail || problem?.title || "Search failed";
    const error = new Error(message) as Error & { status?: number; code?: string };
    error.status = res.status;
    if (problem?.code) error.code = problem.code;
    throw error;
  }

  return res.json() as Promise<Recipient[]>;
}