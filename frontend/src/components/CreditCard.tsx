import Background from '../images/worldmap.png'
import DeveloperBoardIcon from '@mui/icons-material/DeveloperBoard';
import { FaWifi } from "react-icons/fa";
import MasterCardLogo from '../images/Mastercard-logo.svg'

type CreditCardProps = {
  cardNumber: string;
  expiry: string;
  cvv: string;

}

function formatCardNumberDisplay(value: string) {
  return value.replace(/\s+/g, '') // remove spaces
              .replace(/(\d{4})/g, '$1 ') // insert a space every 4 digits
              .trim();
}

export default function CreditCard({cardNumber, expiry, cvv}: CreditCardProps) {


  return (

    // Container
    <div
      className="
        flex flex-col
        justify-between
        w-full max-w-md
        aspect-[16/9]
        rounded-2xl p-6 shadow-lg
        text-white
        bg-[#25253D]/80 backdrop-blur-sm
        bg-center bg-no-repeat bg-cover
      "
      style={{ backgroundImage: `url(${Background})` }}
    >

      {/* Icons header */}
      <div className='flex justify-between items-center'>
        <DeveloperBoardIcon fontSize="large" className="text-[#4B5B98]/70" />

        <FaWifi className="text-[#4B5B98]/70 transform rotate-90 text-2xl"/>
      </div>

       {/* Card Number */}
      <div className="flex justify-between text-3xl tracking-widest">
        {formatCardNumberDisplay(cardNumber || '')
          .split(' ')
          .map((group, i) => (
            <span key={i}>{group}</span>
          ))}
      </div>

      {/* Bottom Line */}
      <div className='flex justify-between'>
        <div className='flex gap-12'>

          {/* Expiry date */}
          <div>
            <p className='text-xs text-[#A2A2A7]'>Expiry date</p>
            <p className='text-sm'>{expiry}</p>
          </div>
      
          {/* CVV */}
          <div>
            <p className='text-xs text-[#A2A2A7]'>CVV</p>
            <p className='text-sm'>{cvv}</p>
          </div>
          
        </div>

        {/* Card Brand */}
        <div className='flex flex-col justify-center items-center'>
          <img src={MasterCardLogo} alt="Mastercard Logo" className='w-10' />
          <p className=''>MasterCard</p>

        </div>
      
      </div>
      

      
    </div>

  )
}