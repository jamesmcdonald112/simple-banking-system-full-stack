import type { ReactNode } from "react"

type ActionButtonProps = {
  icon: ReactNode;
  label: string;
  onClick: () => void;
  disabled?: boolean;
}

export default function ActionButton({ icon, label, onClick, disabled }: ActionButtonProps) {

  return (
    <div className="flex flex-col items-center  text-white">
      <button 
        className="rounded-full border-[#707070] bg-[#1E1E2D] p-5 flex items-center justify-center disabled:opacity-50 disabled:cursor-not-allowed" 
        onClick={onClick} 
        disabled={disabled}
      >
        {icon}
      </button>
      <span className="mt-2 text-sm">
        {label}
      </span>
    </div>
  )

}