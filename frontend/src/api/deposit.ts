export async function deposit(accountId: number, amount: number) {
  const res = await fetch(`${import.meta.env.VITE_API_URL}/api/accounts/${accountId}/deposit`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ amount }),
  });

  if (!res.ok) {
    throw new Error(`Failed to deposit: ${res.status} ${res.statusText}`);
  }

  return res.json();
}