import { useState } from "react";
import Heading1 from "../components/Heading1";
import { useAuth } from "../context/AuthContext";
import { userLogin } from "../api/userLogin";
import CardNumberInput from "../components/CardNumberInput";
import FormContainer from "../components/FormContainer";
import PinInput from "../components/PinInput";
import PasswordInput from "../components/PasswordInput";
import Button from "../components/Button";
import type { Account } from "../types/Account";
import { useNavigate } from "react-router-dom";


export default function Login() {
  const navigate = useNavigate();
  const [accountInfo, setAccountInfo]  = useState<Account | null>(null);
  const {isLoggedIn, logIn} = useAuth();
  const[isError, setIsError] = useState<boolean>(false)
  const [errorMessage, setErrorMessage] = useState<string>('')
  const [isSubmitting, setIsSubmitting] = useState<boolean>(false);

  const [cardNumber, setCardNumber] = useState<string>('');
  const [pin, setPin]= useState<string>('');
  const [password, setPassword]= useState<string>('');


  async function handleLogin(e: React.FormEvent) {
    e.preventDefault();
    setIsError(false);
    setErrorMessage('')
    setIsSubmitting(true);

    try {
      const data = await userLogin({cardNumber, pin, password});
      setAccountInfo(data);
      logIn();
      navigate("/dashboard", {state: data})
    } catch(error) {
      setIsError(true);
      setErrorMessage("Failed to login. Please try again")
    } finally {
      setIsSubmitting(false)
    }
  }

  return (
    <FormContainer onSubmit={handleLogin}>
      <Heading1>Login In</Heading1>

      {/* Card Number */}
      <CardNumberInput
        label="Card Number"
        name="cardNumber"
        value={cardNumber}
        onChange={(e) => setCardNumber(e.target.value)}
        placeholder="4000123456789012"
        required={true}
      />

      {/* PIN */}
      <PinInput 
        id="login-pin"
        name="login-pin"
        value={pin}
        onChange={(e) => setPin(e.target.value)}
        required={true}
      />

      {/* Password */}
      <PasswordInput         
        name="login-password"
        value={password} 
        onChange={(e) => setPassword(e.target.value)}     
      />

      {/* Submit */}
      <Button 
        type="submit"
        disabled={isSubmitting}      
      >
        Login In
      </Button>

    </FormContainer>
  )
}