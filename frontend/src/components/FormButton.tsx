import type { ReactNode } from "react"

type FormButtonType = {
  children: ReactNode;
  disabled: boolean;
}

export default function FormButton({children, disabled}: FormButtonType) {

  return (
    <button 
      disabled={disabled}
      type="submit"
      className="form-submit-button">
        {children}
    </button>
  )
}