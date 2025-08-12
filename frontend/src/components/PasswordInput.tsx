import type { ReactNode } from 'react';
import {MdLock } from 'react-icons/md'



type PasswordInputProps = {
  name: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  className?: string;
  required?: boolean;
}


export default function PasswordInput({name, value, onChange, className, required}: PasswordInputProps) {
 return (
   <div className="flex flex-col">
     <label
       htmlFor={name}
       className="label text-muted"
     >
       Password
     </label>

     <div className='input-icon-wrapper'>
        <div className="icon-muted">{<MdLock />}</div>
        <input
            id={name}
            name={name}
            type="password"
            value={value}
            onChange={onChange}
            className={`input ${className || ""}`}
            placeholder='********'
            required={required}
          />  
     </div>
   </div>
 )
}
