import { useNavigate } from "react-router-dom";
import WelcomeHeader from "../components/WelcomeHeader";
import { useAuth } from "../context/AuthContext";
import Container from "../components/Container";
import CreditCard from "../components/CreditCard";
import Balance from "../components/Balance";

export default function Dashboard() {
  const {account, logOut} = useAuth();

  const navigate = useNavigate()

  if (!account) return null;

 

  return (
    <Container>
      <WelcomeHeader  name={account.name}/>

      <CreditCard 
        cardNumber={account.cardNumber}
        expiry="08/28"
        cvv="6986"
      />

      <Balance 
        accountType="Personal" 
        currency="EUR" 
        balance={100.37}
      />
      <button onClick={logOut}>Log Out</button>
    </Container>
  );
}