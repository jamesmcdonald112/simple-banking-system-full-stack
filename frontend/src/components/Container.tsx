// Container.tsx
type Props = {
  children: React.ReactNode;
  className?: string;
};

export default function Container({ children, className = "" }: Props) {
  return (
    <div
      className={`flex flex-col gap-4 max-w-7xl mx-auto w-full px-6 ${className} items-center`}
    >
      {children}
    </div>
  );
}