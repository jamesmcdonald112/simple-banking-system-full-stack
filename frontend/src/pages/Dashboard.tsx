import { useAuth } from "../context/AuthContext";

export default function Dashboard() {
  const {logOut} = useAuth();
  return (
    <>
      <h1>Dashboard</h1>
      <button onClick={logOut}>Log Out</button>
    </>
  );
}