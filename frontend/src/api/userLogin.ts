// src/api/userLogin.ts
import { BASE_URL } from "../config";
import type { ProblemDetail } from "../types/http";

type LoginPayload = {
  cardNumber: string;
  pin: string;
  password: string;
};

type LoginSuccess = {
  id: number;
  cardNumber: string;
  balance: number;
  name: string;
  email: string;
  phone: string;
};

export async function userLogin(body: LoginPayload): Promise<LoginSuccess> {
  const res = await fetch(`${BASE_URL}/api/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(body),
  });

  if (!res.ok) {
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
      `Login failed (${res.status} ${res.statusText})`;

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

  return (await res.json()) as LoginSuccess;
}