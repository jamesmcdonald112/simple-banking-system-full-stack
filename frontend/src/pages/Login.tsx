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
import { toast } from "react-hot-toast";


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

    const payload = {
      cardNumber: cardNumber.trim(),
      pin: pin.trim(),
      password: password
    };
    if (!payload.cardNumber || !payload.pin || !payload.password) {
      setIsSubmitting(false);
      setIsError(true);
      setErrorMessage("Please fill in card number, PIN and password.");
      toast.error("Please fill in card number, PIN and password.");
      return;
    }

    try {
      const data = await userLogin(payload);      
      logIn(data);
      toast.success("Logged in");
      navigate("/dashboard")
    } catch (error) {
      const e = error as Error & { status?: number };
      let message = e.message || "Login failed";
      if (typeof e.status === "number") {
        if (e.status === 401) {
          message = "Invalid card number, PIN or password.";
        } else if (e.status >= 500) {
          message = "Server error. Please try again later.";
        }
      }
      setIsError(true);
      setErrorMessage(message);
      toast.error(message);
    } finally {
      setIsSubmitting(false)
    }
  }

  return (
    <FormContainer onSubmit={handleLogin}>
      <Heading1>Log in</Heading1>

      {/* Error message */}
      {isError && (
        <div className="rounded-md border border-red-500/50 bg-red-50 text-red-800 px-3 py-2 text-sm" aria-live="polite">
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


      <div className="mt-4 flex flex-col gap-3">
        {/* Submit */}
        <Button
          type="submit"
          disabled={isSubmitting || !cardNumber.trim() || !pin.trim() || !password.trim()}
          aria-busy={isSubmitting}
        >
          {isSubmitting ? "Logging in…" : "Log in"}
        </Button>

        {/* Create an account */}
        <Link
          to="/create-account"
          className="block text-center text-blue-500 hover:underline"
        >
          Create an account?
        </Link>
      </div>
      

    </FormContainer>
  )
}