import { useState } from "react";
import { createAccount } from "../api/createAccount";
import TextInput from "../components/TextInput";
import { MdEmail, MdPerson, MdPhone } from 'react-icons/md'
import PhoneInput from "../components/PhoneInput";
import EmailInput from "../components/EmailInput";
import PasswordInput from "../components/PasswordInput";
import FormContainer from "../components/FormContainer";
import Heading1 from "../components/Heading1";
import Button from "../components/Button";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "react-hot-toast";



export default function CreateAccount() {
  
  // Local state management
  const [isError, setIsError] = useState<boolean>(false);
  const [errorMessage, setErrorMessage] = useState<string>("");
  const [isSubmitting, setIsSubmitting] = useState<boolean>(false);
  const [name, setName] = useState<string>('');
  const [phone, setPhone] = useState<string>('');
  const [email, setEmail] = useState<string>('');
  const [password, setPassword] = useState<string>('');

  const navigate = useNavigate()

  // Handle form submission
  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault()
    setIsError(false);
    setErrorMessage("");
    setIsSubmitting(true);

    try {
      const payload = {
        name: name.trim(),
        phone: phone.trim(),
        email: email.trim(),
        password
      };
      // Basic client-side validation
      if (!payload.name || !payload.email || !payload.phone || !payload.password) {
        setIsError(true);
        const msg = "Please fill in all required fields.";
        setErrorMessage(msg);
        toast.error(msg);
        setIsSubmitting(false);
        return;
      }
      const data = await createAccount(payload);
      toast.success("Account created successfully");
      navigate("/account-created", {state: data})
    } catch (error: unknown) {
      const msg = error instanceof Error ? error.message : "Failed to create account. Please try again.";
      toast.error(msg);
      setIsError(true);
      setErrorMessage(msg);
    } finally {
      setIsSubmitting(false)
    }
  }


    
//  Render Component
 return (
    <FormContainer onSubmit={handleSubmit}>
      <div
        className="mb-3 rounded border border-yellow-300 bg-yellow-50 text-yellow-800 px-3 py-2 text-sm"
        aria-live="polite"
        role="alert"
      >
        ⚠️ This is a test project. Please do not use real personal information. Note: The server may take up to 60 seconds to respond (free Render tier).
      </div>
      <Heading1>Create Account</Heading1>      
      {isError && (
        <div
          className="mb-3 rounded border border-red-300 bg-red-50 text-red-800 px-3 py-2 text-sm"
          aria-live="polite"
          role="alert"
        >
          {errorMessage}
        </div>
      )}

        {/* Name */}
        <TextInput 
          label="Full Name"
          name="fullName"
          value={name}
          icon={<MdPerson/>}
          onChange={(e) => setName(e.target.value)}
          placeholder="John Doe"
          required={true}
        />

        {/* Phone */}
        <PhoneInput 
          label="Phone Number"
          name="phoneNumber"
          value={phone}
          icon={<MdPhone />}
          onChange={(e) => setPhone(e.target.value)}
          placeholder="+353 85 435 6748"
          required={true}
        />

        {/* Email */}
        <EmailInput 
          label="Email"
          name="email"
          value={email}
          icon={<MdEmail />}
          onChange={(e) => setEmail(e.target.value)}
          placeholder='johndoe1234@email.com'
          required={true}
        />

        {/* Password */}
        <PasswordInput          
          name="password"
          value={password}          
          onChange={(e) => setPassword(e.target.value)}       
          required={true}
        />

        <div className="flex flex-col gap-3 w-full">
          {/* Submit Button */}
          <Button
            type="submit"
            aria-busy={isSubmitting}
            disabled={isSubmitting || !name.trim() || !email.trim() || !phone.trim() || !password.trim()}
            aria-disabled={isSubmitting}
            
          >
            {isSubmitting ? "Creating..." : "Create Account"}
          </Button>
          <Link
            to="/login"
            className="text-blue-500 hover:underline text-center"
          >
            Already have an account?
          </Link>
        </div>
           

    </FormContainer>
 )
}
