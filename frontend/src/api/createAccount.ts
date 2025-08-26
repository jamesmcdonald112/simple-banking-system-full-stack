import { BASE_URL } from "../config";
import type { Account } from "../types/Account";

type CreateAccountProps = {
  name: string;
  phone: string;
  email: string;
  password: string;
};

export async function createAccount({
  name,
  phone,
  email,
  password,
}: CreateAccountProps): Promise<Account> {
  const res = await fetch(`${BASE_URL}/api/accounts`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ name, email, phone, password }),
  });

 if (!res.ok) {
  let message = "Failed to create account";
  try {
    const data = await res.json();
    if (data?.detail) message = data.detail;
    else if (data?.title) message = data.title; // pick up ProblemDetail "title"
    else if (data?.message) message = data.message;
  } catch {
    // fallback to default message
  }
  const error = new Error(message) as Error & { status?: number };
  error.status = res.status;
  throw error;
}

  return res.json();
}