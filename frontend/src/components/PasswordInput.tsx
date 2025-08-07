import type { ReactNode } from 'react';


type PasswordInputProps = {
  label: string;
  name: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  icon?: ReactNode;
  className?: string;
  required?: boolean;
}


export default function PasswordInput({label, name, value, onChange,  icon, className, required}: PasswordInputProps) {
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
            type="password"
            value={value}
            onChange={onChange}
            className={`form-input ${className || ""}`}
            placeholder='********'
            required={required}
          />  
     </div>
   </div>
 )
}
