import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function PublicRoute() {
  const {isLoggedIn} = useAuth()
  return isLoggedIn ? <Navigate to="/dashboard" replace /> : <Outlet />;
}