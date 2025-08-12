import { MdOutlineFiberPin } from 'react-icons/md';


type PinInputProps = {
  id?: string;
  name?: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  className?: string;
  required?: boolean;
}


export default function PinInput({ 
  id,
  name,
  value, 
  onChange,   
  className, 
  required}: PinInputProps) {
 return (
   <div className="flex flex-col">
     <label
       htmlFor="pin"
       className="label text-muted"
     >
       PIN
     </label>

     <div className='input-icon-wrapper'>
        <div className="icon-muted">{<MdOutlineFiberPin />}</div>
        <input
            id={id}
            name={name}
            type="text"
            value={value}
            onChange={onChange}
            className={`input ${className || ""}`}
            placeholder="****"
            required={required}
            max={4}
            min={4}
            pattern='\d{4}'
            title="Card number must be 4 digits"
          />  
     </div>
   </div>
 )
}
