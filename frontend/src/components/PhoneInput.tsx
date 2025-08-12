import type { ReactNode } from 'react';


type PhoneInputProps = {
  label: string;
  name: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  icon?: ReactNode;
  className?: string;
  placeholder?: string;
  required?: boolean;
}


export default function PhoneInput({label, name, value, onChange,  icon, className, placeholder, required}: PhoneInputProps) {
 return (
   <div className="flex flex-col">
     <label
       htmlFor={name}
       className="label text-muted"
     >
       {label}
     </label>

     <div className='input-icon-wrapper'>
        <div className="icon-muted">{icon}</div>
        <input
            id={name}
            name={name}
            type="tel"
            pattern="^\+?[0-9\s]{7,15}$"
            value={value}
            onChange={onChange}
            className={`input ${className || ""}`}
            placeholder={placeholder}
            required={required}
          />  
     </div>
   </div>
 )
}
