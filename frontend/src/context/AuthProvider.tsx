import { useState, type ReactNode } from "react";
import { AuthContext } from "./AuthContext";
import type { Account } from "../types/Account";

type AuthProviderProps = { children: ReactNode };

export function AuthProvider({ children }: AuthProviderProps) {
  const [account, setAccount] = useState<Account | null>(() => {

    const raw = localStorage.getItem("account");
    try {
      return raw ? (JSON.parse(raw) as Account) : null;
    } catch {
      return null;
    }
  });

  const isLoggedIn = !!account;

  const logIn = (accountData: Account) => {
    setAccount(accountData);
    localStorage.setItem("account", JSON.stringify(accountData));
  };

  const logOut = () => {
    setAccount(null);
    localStorage.removeItem("account");
  };

  return (
    <AuthContext.Provider value={{ account, isLoggedIn, logIn, logOut }}>
      {children}
    </AuthContext.Provider>
  );
}