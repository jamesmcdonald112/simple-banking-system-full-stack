import { BASE_URL } from "../config";
import type { ProblemDetail } from "../types/http";
import { toast } from "react-hot-toast";

type LoginPayload = {
  cardNumber: string;
  pin: string;
  password: string;
}

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
    body: JSON.stringify(body)
  });

  if (!res.ok) {
    let problem: ProblemDetail | undefined;

    try {
      problem = await res.json();
    } catch (err) {
        console.warn("Could not parse error response as JSON", err);

    }

    const message = problem?.detail || problem?.title || "Login failed";
    toast.error(message);
    const error = new Error(message) as Error & { status?: number; code?: string };
    error.status = res.status;
    if (problem?.code) error.code = problem.code;
    throw error;
  }

  const result = await res.json() as LoginSuccess;
  toast.success("Login successful");
  return result;
}