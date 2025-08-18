import { useNavigate } from "react-router-dom";
import WelcomeHeader from "../components/WelcomeHeader";
import { useAuth } from "../context/AuthContext";
import Container from "../components/Container";
import CreditCard from "../components/CreditCard";
import Balance from "../components/Balance";
import { FaArrowUp, FaPlus } from "react-icons/fa6";
import ActionButton from "../components/ActionButton";


export default function Dashboard() {
  const {account, logOut} = useAuth();

  const navigate = useNavigate()

  if (!account) return null;

  function handleTransfer() {

  }

  function handleAddMoney() {

  }

  function handleViewCardDetails() {

  }
 

  return (
    <Container>
      <div className="flex flex-col gap-6 md:gap-8">
        <WelcomeHeader  name={account.name}/>

        <div className="w-full max-w-lg mx-auto">
          <CreditCard 
            cardNumber={account.cardNumber}
            expiry="08/28"
            cvv="6986"
          />
        </div>

        <div className="w-full max-w-lg mx-auto mt-4 md:mt-6">
          <Balance 
            accountType="Personal" 
            currency="EUR" 
            balance={100.37}
          />
        </div>

        {/* Buttons */}
        <div className="grid grid-cols-3 gap-4 w-full max-w-lg mx-auto mt-6 md:mt-8">
          <ActionButton
            icon={<FaPlus />}
            label="Add Money"
            onClick={handleAddMoney}
          />
          <ActionButton
            icon={<FaArrowUp />}
            label="Transfer"
            onClick={handleTransfer}
          />
          <ActionButton
            icon={<FaArrowUp />}
            label="Send"
            onClick={handleViewCardDetails}
          />
        </div>

        <button onClick={logOut}>Log Out</button>
      </div>
    </Container>
  );
}