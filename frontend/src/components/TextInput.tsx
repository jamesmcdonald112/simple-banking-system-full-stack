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
       className="label text-muted"
     >
       {label}
     </label>

     <div className='input-icon-wrapper'>
        <div className="icon-muted">{icon}</div>
        <input
            id={name}
            name={name}
            type="text"
            value={value}
            onChange={onChange}
            className={`input ${className || ""}`}
            placeholder='John Doe'
            required={required}
          />  
     </div>
   </div>
 )
}
