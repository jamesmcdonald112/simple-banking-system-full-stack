import { BASE_URL } from "../config";
import type { Account } from "../types/Account";

type createAccountProps = {
  name: string;
  phone: string;
  email: string;
  password: string;
}


export async function createAccount({name, phone, email, password}: createAccountProps): Promise<Account> {
  const res = await fetch(`${BASE_URL}/api/accounts`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({name, email, phone, password})
  });

   console.log(res)

  if (!res.ok) {
    throw new Error("Failed to create account");
  }

  return res.json();
}