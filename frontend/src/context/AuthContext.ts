// AuthContext.ts
import { createContext, useContext } from "react";

type AuthContextType = {
  isLoggedIn: boolean;
  logIn: () => void;
  logOut: () => void
};

const AuthContext = createContext<AuthContextType | null>(null);

function useAuth() {
  const context = useContext(AuthContext);
  if (!context) throw new Error("useAuth must be used within an AuthProvider");
  return context;
  
}

export {AuthContext, useAuth};