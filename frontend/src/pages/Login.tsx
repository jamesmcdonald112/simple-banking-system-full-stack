import { useAuth } from "../context/AuthContext"

export default function Login() {
  const {isLoggedIn, logIn} = useAuth();


  return (
    <>
      <h1>Login page</h1>
      <button onClick={logIn}>Login</button>
      <p>Login Status: {isLoggedIn.toString()}</p>
    </>
  )
}