import { useState } from "react";
import type { Account } from "../types/Account";
import { createAccount } from "../api/createAccount";
import TextInput from "../components/TextInput";
import { MdEmail, MdPerson, MdPhone, MdLock } from 'react-icons/md'
import PhoneInput from "../components/PhoneInput";
import EmailInput from "../components/EmailInput";
import PasswordInput from "../components/PasswordInput";
import FormButton from "../components/FormButton";
import FormContainer from "../components/FormContainer";



export default function CreateAccount() {
  
  // Local state management
  const [accountInfo, setAccountInfo]  = useState<Account | null>(null);
  const[isError, setIsError] = useState<boolean>(false)
  const [errorMessage, setErrorMessage] = useState<string>('')
  const [isSubmitting, setIsSubmitting] = useState<boolean>(false);
  const [name, setName] = useState<string>('')
  const [phone, setPhone] = useState<string>('')
  const [email, setEmail] = useState<string>('')
  const [password, setPassword] = useState<string>('')

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
    <FormContainer>
      <h1 className="text-white text-2xl tracking-wide">Create Account</h1>
      <form onSubmit={handleSubmit} className="flex flex-col gap-8">

        {/* Name */}
        <TextInput 
          label="Full Name"
          name="fullName"
          value={name}
          icon={<MdPerson/>}
          onChange={(e) => setName(e.target.value)}
          required={true}
        />

        {/* Phone */}
        <PhoneInput 
          label="Phone Number"
          name="phoneNumber"
          value={phone}
          icon={<MdPhone />}
          onChange={(e) => setPhone(e.target.value)}
          required={true}
        />

        {/* Email */}
        <EmailInput 
          label="Email"
          name="email"
          value={email}
          icon={<MdEmail />}
          onChange={(e) => setEmail(e.target.value)}
          required={true}
        />

        {/* Password */}
        <PasswordInput
          label="Password"
          name="password"
          value={password}
          icon={<MdLock />}
          onChange={(e) => setPassword(e.target.value)}
          required={true}
        />

        {/* Submit Button */}
        <FormButton
          disabled={isSubmitting}
        >
          {isSubmitting ? "Creating..." : "Create Account"}
        </FormButton>        
      </form>

      {successMessage}

      {errorFeedback}   
    </FormContainer>
 )
}
