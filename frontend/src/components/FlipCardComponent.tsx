import CreditCard from "./CreditCard";
import Balance from "./Balance";

type FlipCardComponentProps = {
  isFlipped: boolean;
  cardNumber: string;
  expiry: string;
  cvv: string;
  accountType: string;
  currency: string;
  balance: number;
};

// Based on: https://dev.to/mematthew123/how-to-3d-flip-cards-using-tailwind-css-a2f

export default function FlipCardComponent({
  isFlipped,
  cardNumber,
  expiry,
  cvv,
  accountType,
  currency,
  balance,
}: FlipCardComponentProps) {
  return (
    // Outer wrapper provides perspective
    <div className={`[perspective:1000px]`}>
      {/* Flipper: grid stacks faces on the same cell; rotate on isFlipped */}
      <div
        className={`grid transition-transform duration-500 [transform-style:preserve-3d] ${
          isFlipped ? "[transform:rotateY(180deg)]" : ""
        }`}
      >
        {/* Front face */}
        <div className="col-start-1 row-start-1 flex items-center justify-center [backface-visibility:hidden]">
          <Balance accountType={accountType} currency={currency} balance={balance} />
        </div>

        {/* Back face */}
        <div className="col-start-1 row-start-1 [transform:rotateY(180deg)] [backface-visibility:hidden]">
          <CreditCard cardNumber={cardNumber} expiry={expiry} cvv={cvv} />
        </div>
      </div>
    </div>
  );
}