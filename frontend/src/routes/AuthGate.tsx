import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function AuthGate() {
  const {isLoggedIn} = useAuth();
  return <Navigate to={isLoggedIn ? "/dashboard" : "/login"} replace />
}