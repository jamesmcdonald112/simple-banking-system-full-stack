import type { ReactNode } from 'react';


type TextInputProps = {
  label: string;
  name: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  icon?: ReactNode;
  className?: string;
  required?: boolean;
}


export default function TextInput({label, name, value, onChange,  icon, className, required}: TextInputProps) {
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
            type="text"
            value={value}
            onChange={onChange}
            className={`form-input ${className || ""}`}
            placeholder='John Doe'
            required={required}
          />  
     </div>
   </div>
 )
}
