import { useState } from "react";
import type { Account } from "../types/Account";
import { createAccount } from "../api/createAccount";


export default function CreateAccount() {
  
  // Local state management
  const [accountInfo, setAccountInfo]  = useState<Account | null>(null);
  const[isError, setIsError] = useState<boolean>(false)
  const [errorMessage, setErrorMessage] = useState<string>('')
  const [isSubmitting, setIsSubmitting] = useState<boolean>(false);

  // Hanlde form submission
  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault()
    setIsError(false);
    setErrorMessage("");
    setIsSubmitting(true);

    try {
      const data = await createAccount();
      setAccountInfo(data);
    } catch(error) {
      setIsError(true);
      setErrorMessage("Failed to create account. Please try again")
    } finally {
      setIsSubmitting(false)
    }
  }

  // Render feedback messages
 const successMessage = accountInfo && !isError && (
  <div>
    <p>Card Number: {accountInfo.cardNumber}</p>
    <p>PIN: {accountInfo.pin}</p>
  </div>
 )

 const errorFeedback = isError && errorMessage && (
  <div>
    <p>{errorMessage}</p>
  </div>
 )
    
//  Render Component
 return (
    <div 
      className="
        mx-auto max-w-md 
        p-6 
        border-2 border-orange-600 rounded-xl
        ">
      <h1>Create Account</h1>
      <form onSubmit={handleSubmit}>
        <button 
          disabled={isSubmitting} 
          type="submit">
            {isSubmitting ? "Creating..." : "Create Account"}
        </button>
      </form>

      {successMessage}

      {errorFeedback}   
    </div>
 )
}
