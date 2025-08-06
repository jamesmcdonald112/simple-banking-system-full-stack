type TextInputProps = {
 name: string;
 value: string;
 onChange: (e: React.ChangeEvent<HTMLInputElement>) => VoidFunction;
 className?: string;
}


export default function TextInput({name, value, onChange, className}: TextInputProps) {
 return (
   <div>
     <label
       htmlFor={name}
     >
       {name}:
     </label>
     <input
         id={name}
         name={name}
         type="text"
         value={value}
         onChange={onChange}
         className={className}
       />  
   </div>
 )
}
