import { useState } from "react";
import Heading1 from "../components/Heading1";
import { useAuth } from "../context/AuthContext";
import { userLogin } from "../api/userLogin";
import CardNumberInput from "../components/CardNumberInput";
import FormContainer from "../components/FormContainer";
import PinInput from "../components/PinInput";
import PasswordInput from "../components/PasswordInput";
import Button from "../components/Button";
import { Link, useNavigate } from "react-router-dom";


export default function Login() {
  const navigate = useNavigate();
  const { logIn } = useAuth();

  const [cardNumber, setCardNumber] = useState<string>('');
  const [pin, setPin]= useState<string>('');
  const [password, setPassword]= useState<string>('');

  const [isSubmitting, setIsSubmitting] = useState<boolean>(false);
  const[isError, setIsError] = useState<boolean>(false)
  const [errorMessage, setErrorMessage] = useState<string>('')

  


  async function handleLogin(e: React.FormEvent) {
    e.preventDefault();
    setIsError(false);
    setErrorMessage('')
    setIsSubmitting(true);

    try {
      const data = await userLogin({cardNumber, pin, password});      
      logIn(data);
      navigate("/dashboard")
    } catch(error) {
      const e = error as Error & { status?: number };
      setIsError(true);
      setErrorMessage(e.message || "Login failed");
    } finally {
      setIsSubmitting(false)
    }
  }

  return (
    <FormContainer onSubmit={handleLogin}>
      <Heading1>Login In</Heading1>

      {/* Error message */}
      {isError && (
        <div className="rounded-md border border-red-500/50 bg-red-500/10 text-red-300 px-3 py-2 text-sm">
          {errorMessage}
        </div>
      )}

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


      <div className="flex justify-between items-center">
        {/* Submit */}
        <Button 
          type="submit"
          disabled={isSubmitting}      
        >
          Log In
        </Button>

        {/* Create an account */}
        <Link 
          to="/create-account" 
          className="text-blue-500 hover:underline ml-4"
        >
          Create an account?
        </Link>
      </div>
      

    </FormContainer>
  )
}