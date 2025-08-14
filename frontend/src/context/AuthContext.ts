// AuthContext.ts
import { createContext, useContext } from "react";
import type { Account } from "../types/Account";

type AuthContextType = {
  account: Account | null;
  isLoggedIn: boolean;
  logIn: (account: Account) => void;
  logOut: () => void
};

const AuthContext = createContext<AuthContextType | null>(null);

function useAuth() {
  const context = useContext(AuthContext);
  if (!context) throw new Error("useAuth must be used within an AuthProvider");
  return context;
  
}

export {AuthContext, useAuth};