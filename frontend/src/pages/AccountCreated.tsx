import { useLocation, useNavigate } from "react-router-dom";
import Button from "../components/Button";
import Heading1 from "../components/Heading1";
import InfoField from "../components/InfoField";
import { MdEmail, MdPerson, MdPhone, MdCreditCard, MdOutlineFiberPin } from 'react-icons/md'
import { useEffect } from "react";
import Container from "../components/Container";


export default function AccountCreated() {
  const location = useLocation();
  const navigate = useNavigate();


  const { name, email, phone, cardNumber, pin } = location.state || {};
  console.log("State", location.state)

  useEffect(() => {
    if(!location.state) {
      navigate("/create-account");
    }
  }, [location, navigate]);

  return (
    <Container className="flex flex-col gap-2">
      <Heading1>Account Created</Heading1>

      {/* Name */}
      <InfoField 
        label="Name"
        icon={<MdPerson />}
        info={name}
      />

      {/* Email */}
      <InfoField 
        label="Email"
        icon={<MdEmail />}
        info={email}
      />

      {/* Phone */}
      <InfoField 
        label="Phone"
        icon={<MdPhone />}
        info={phone}
      />

      {/* Card Number */}
      <InfoField 
        label="Card Number"
        icon={<MdCreditCard />}
        info={cardNumber}
      />

      {/* PIN */}
      <InfoField 
        label="PIN"
        icon={<MdOutlineFiberPin />}
        info={pin}
      />

      <Button
        type="button"
        onClick={() => navigate("/login")}
      >
        Go To Login
      </Button>

    </Container>
  )
}