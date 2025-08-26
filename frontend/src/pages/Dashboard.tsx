import AddMoneyModal from "../components/AddMoneyModal";
import WelcomeHeader from "../components/WelcomeHeader";
import { useAuth } from "../context/AuthContext";
import Container from "../components/Container";
import { FaArrowUp, FaPlus, FaEuroSign } from "react-icons/fa6";
import { RiBankCardFill } from "react-icons/ri";
import ActionButton from "../components/ActionButton";
import FlipCardComponent from "../components/FlipCardComponent";
import { useState } from "react";
import TransferModal from "../components/TransferModal";
import type { TransferResponse } from "../api/transfers";

export default function Dashboard() {
  const { account, logOut, logIn } = useAuth();
  const [isFlipped, setIsFlipped] = useState<boolean>(false);
  const [showAddMoney, setShowAddMoney] = useState(false);
  const [showTransfer, setShowTransfer] = useState(false);

  if (!account) return null;

  return (
    <Container>
      <div className="flex flex-col gap-6 md:gap-8">
        <WelcomeHeader name={account.name} />

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
          <div className="transition-transform transform hover:scale-105 hover:shadow-lg">
            <ActionButton
              icon={<FaPlus />}
              label="Add Money"
              onClick={() => setShowAddMoney(true)}
            />
          </div>

          {/* Transfer */}
          <div className="transition-transform transform hover:scale-105 hover:shadow-lg">
            <ActionButton
              icon={<FaArrowUp />}
              label="Transfer"
              onClick={() => setShowTransfer(true)}
            />
          </div>

          {/* Show Balance */}
          {!isFlipped && (
            <div className="transition-transform transform hover:scale-105 hover:shadow-lg">
              <ActionButton
                icon={<RiBankCardFill />}
                label="View Card"
                onClick={() => setIsFlipped(!isFlipped)}
              />
            </div>
          )}

          {/* Show Card */}
          {isFlipped && (
            <div className="transition-transform transform hover:scale-105 hover:shadow-lg">
              <ActionButton
                icon={<FaEuroSign />}
                label="View Balance"
                onClick={() => setIsFlipped(!isFlipped)}
              />
            </div>
          )}
        </div>

        {/* Add Money Modal */}
        <AddMoneyModal
          open={showAddMoney}
          onClose={() => setShowAddMoney(false)}
          account={account}
          onDeposited={(newBalance, updated) => {
            logIn({ ...account, ...(updated ?? {}), balance: newBalance });
          }}
        />

        {/* Transfer Modal */}
       <TransferModal
          open={showTransfer}
          onClose={() => setShowTransfer(false)}
          fromAccountId={account.id}
          onSuccess={(r: TransferResponse) => {
            const newFromBalance = Number(r.fromBalance);
            logIn({ ...account, balance: newFromBalance });
          }}
        />

        <button onClick={logOut}>Log Out</button>
      </div>
    </Container>
  );
}