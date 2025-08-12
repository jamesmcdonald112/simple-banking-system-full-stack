import { BASE_URL } from "../config";
import type { Account } from "../types/Account";

type userLoginProps = {
  cardNumber: string;
  pin: string;
  password: string;
}

export async function userLogin({cardNumber, pin, password}: userLoginProps): Promise<Account> {
  const res = await fetch(`${BASE_URL}/api/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({cardNumber, pin, password})
  });

   console.log(res)

  if (!res.ok) {
    throw new Error("Failed to create account");
  }

  return res.json();
}