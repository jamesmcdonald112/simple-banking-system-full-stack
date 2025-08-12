import type { ReactNode } from 'react';
import { MdCreditCard } from 'react-icons/md';


type CardNumberInputProps = {
  label: string;
  name: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  className?: string;
  placeholder?: string;
  required?: boolean;

}


export default function CardNumberInput({
  label, 
  name, 
  value, 
  onChange,   
  className, 
  placeholder, 
  required}: CardNumberInputProps) {
 return (
   <div className="flex flex-col">
     <label
       htmlFor={name}
       className="label text-muted"
     >
       {label}
     </label>

     <div className='input-icon-wrapper'>
        <div className="icon-muted">{<MdCreditCard />}</div>
        <input
            id={name}
            name={name}
            type="text"
            value={value}
            onChange={onChange}
            className={`input ${className || ""}`}
            placeholder={placeholder}
            required={required}
            max={16}
            min={16}
            pattern='\d{16}'
            title="Card number must be 16 digits"
          />  
     </div>
   </div>
 )
}
