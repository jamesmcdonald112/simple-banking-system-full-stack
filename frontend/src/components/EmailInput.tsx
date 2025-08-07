import type { ReactNode } from 'react';


type EmailInputProps = {
  label: string;
  name: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  icon?: ReactNode;
  className?: string;
  required?: boolean;
}


export default function EmailInput({label, name, value, onChange,  icon, className, required}: EmailInputProps) {
 return (
   <div className="flex flex-col">
     <label
       htmlFor={name}
       className="form-label"
     >
       {label}
     </label>

     <div className='form-input-icon-wrapper'>
        <div className="form-input-icon">{icon}</div>
        <input
            id={name}
            name={name}
            type="email"
            autoComplete='email'
            value={value}
            onChange={onChange}
            className={`form-input ${className || ""}`}
            placeholder='johndoe1234@email.com'
            required={required}
          />  
     </div>
   </div>
 )
}
