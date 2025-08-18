type BalanceProps = {
  balance: number;
  accountType: string;
  currency: string;
}

export default function Balance({ accountType, currency, balance }: BalanceProps) {
  const formattedBalance = balance.toLocaleString("en-IE", {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
  });
  const [integerPart, decimalPart] = formattedBalance.split(".");

  return (
    <div className="flex flex-col text-white space-y-1 items-center">
      
      {/* Account type and currency */}
      <div className="flex items-center gap-2 text-sm text-gray-400">
        <h2 className="font-medium">{accountType}</h2>
        <span className="text-gray-500">•</span>
        <p className="uppercase">{currency}</p>
      </div>

      {/* Balance */}
      <div className="flex items-baseline">
        <span className="text-7xl font-semibold tracking-tight flex items-baseline">
          €
          <span>{integerPart}</span>
          <span className="text-4xl font-normal ml-1">.{decimalPart}</span>
        </span>
      </div>
    </div>
  )
}