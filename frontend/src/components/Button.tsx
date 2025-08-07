import type { ReactNode } from "react"

type FormButtonType = {
  children: ReactNode;
  type: "reset" | "submit" | "button";
  disabled?: boolean;
  onClick?: () => void
}

export default function FormButton({children, type, disabled, onClick}: FormButtonType) {

  return (
    <button 
      disabled={disabled}
      type={type}
      onClick={onClick}
      className="button-submit">
        {children}
    </button>
  )
}