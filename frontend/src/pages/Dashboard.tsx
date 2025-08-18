import { useNavigate } from "react-router-dom";
import WelcomeHeader from "../components/WelcomeHeader";
import { useAuth } from "../context/AuthContext";
import Container from "../components/Container";
import CreditCard from "../components/CreditCard";
import Balance from "../components/Balance";
import { FaArrowUp, FaPlus, FaEuroSign } from "react-icons/fa6";
import { RiBankCardFill } from "react-icons/ri";
import ActionButton from "../components/ActionButton";
import FlipCardComponent from "../components/FlipCardComponent";
import { useState } from "react";


export default function Dashboard() {
  const {account, logOut} = useAuth();

  const [isFlipped, setIsFlipped] = useState<boolean>(false);

  const navigate = useNavigate()

  if (!account) return null;

  function handleTransfer() {

  }

  function handleAddMoney() {

  }

 

  return (
    <Container>
      <div className="flex flex-col gap-6 md:gap-8">
        <WelcomeHeader  name={account.name}/>

        <FlipCardComponent
          isFlipped={isFlipped}
          cardNumber={account.cardNumber}
          expiry="08/28"
          cvv="6986"
          accountType="Personal"
          currency="EUR"
          balance={account.balance}    
        />

        {/* Buttons */}
        <div className="grid grid-cols-3 gap-4 w-full max-w-lg mx-auto mt-6 md:mt-8">

          {/* Add money */}
          <ActionButton
            icon={<FaPlus />}
            label="Add Money"
            onClick={handleAddMoney}
          />

          {/* Transfer */}
          <ActionButton
            icon={<FaArrowUp />}
            label="Transfer"
            onClick={handleTransfer}
          />

          {/* Show Balance */}
          {!isFlipped && 
            <ActionButton
              icon={<RiBankCardFill/>}
              label="View Card"
              onClick={() => setIsFlipped(!isFlipped)}
            />
          }   

          {/* Show Card */}
          {isFlipped && 
            <ActionButton
              icon={<FaEuroSign />}
              label="View Balance"
              onClick={() => setIsFlipped(!isFlipped)}
            />
          }

        </div>

        <button onClick={logOut}>Log Out</button>
      </div>
    </Container>
  );
}