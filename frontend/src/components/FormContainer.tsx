type Props = {
  children: React.ReactNode;
  onSubmit: (e: React.FormEvent<HTMLFormElement>) => void;
  className?: string;
};

export default function FormContainer({ children, onSubmit, className = "" }: Props) {
  return (
    <form onSubmit={onSubmit} className={`flex flex-col gap-4 mx-auto max-w-2xl min-w-xs p-6 ${className}`}>
      {children}
    </form>
  );
}