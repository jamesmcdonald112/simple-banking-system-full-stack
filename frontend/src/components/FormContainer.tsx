// FormContainer.tsx
type Props = {
  children: React.ReactNode;
  className?: string;
};

export default function FormContainer({ children, className = "" }: Props) {
  return (
    <div className={`flex flex-col gap-4 mx-auto max-w-2xl min-w-xs p-6 ${className}`}>
      {children}
    </div>
  );
}