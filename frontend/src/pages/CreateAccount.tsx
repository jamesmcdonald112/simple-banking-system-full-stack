import { useState } from "react";
import type { Account } from "../types/Account";
import { createAccount } from "../api/createAccount";
import TextInput from "../components/TextInput";
import { MdEmail, MdPerson, MdPhone, MdLock } from 'react-icons/md'
import PhoneInput from "../components/PhoneInput";
import EmailInput from "../components/EmailInput";
import PasswordInput from "../components/PasswordInput";
import FormContainer from "../components/FormContainer";
import Heading1 from "../components/Heading1";
import Button from "../components/Button";
import { useNavigate } from "react-router-dom";



export default function CreateAccount() {
  
  // Local state management
  const [accountInfo, setAccountInfo]  = useState<Account | null>(null);
  const[isError, setIsError] = useState<boolean>(false)
  const [errorMessage, setErrorMessage] = useState<string>('')
  const [isSubmitting, setIsSubmitting] = useState<boolean>(false);
  const [name, setName] = useState<string>('John Doe');
  const [phone, setPhone] = useState<string>('+353123456789');
  const [email, setEmail] = useState<string>('john@example.com');
  const [password, setPassword] = useState<string>('password123');

  const navigate = useNavigate()

  // Hanlde form submission
  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault()
    setIsError(false);
    setErrorMessage("");
    setIsSubmitting(true);

    try {
      const data = await createAccount({name, phone, email, password});
      setAccountInfo(data);
      navigate("/account-created", {state: data})
    } catch(error) {
      setIsError(true);
      setErrorMessage("Failed to create account. Please try again")
    } finally {
      setIsSubmitting(false)
    }
  }


    
//  Render Component
 return (
    <FormContainer onSubmit={handleSubmit}>
      <Heading1>Create Account</Heading1>      

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

        {/* Submit Button */}
        <Button
        type="submit"
          disabled={isSubmitting}
        >
          {isSubmitting ? "Creating..." : "Create Account"}
        </Button>        

    </FormContainer>
 )
}
