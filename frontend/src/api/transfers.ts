import { BASE_URL } from "../config";
import type { ProblemDetail } from "../types/http";
import toast from "react-hot-toast";

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

export async function createTransfer(body: TransferRequest): Promise<TransferResponse> {
  const res = await fetch(`${BASE_URL}/api/transfers`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(body),
  });

  if (!res.ok) {
    let problem: ProblemDetail | undefined;
    try { problem = await res.json(); } catch {}
    const message = problem?.detail || problem?.title || "Transfer failed";
    toast.error(message); 
    const error = new Error(message) as Error & { status?: number; code?: string };
    error.status = res.status;
    if (problem?.code) error.code = problem.code;
    throw error;
  }

  const result = (await res.json()) as TransferResponse;
  toast.success("Transfer completed successfully"); 
  return result;
}