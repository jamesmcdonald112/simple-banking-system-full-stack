import { BASE_URL } from "../config";
import type { Account } from "../types/Account";
import { toast } from "react-hot-toast";

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

  if (!res.ok) {
    toast.error("Failed to create account");
    throw new Error("Failed to create account");
  }

  toast.success("Account created successfully");

  return res.json();
}