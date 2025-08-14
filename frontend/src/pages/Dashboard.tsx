import { useNavigate } from "react-router-dom";
import WelcomeHeader from "../components/WelcomeHeader";
import { useAuth } from "../context/AuthContext";
import Container from "../components/Container";

export default function Dashboard() {
  const {account, logOut} = useAuth();

  const navigate = useNavigate()

  if (!account) return null;

 

  return (
    <Container>
      <WelcomeHeader  name={account.name}/>
      <button onClick={logOut}>Log Out</button>
    </Container>
  );
}