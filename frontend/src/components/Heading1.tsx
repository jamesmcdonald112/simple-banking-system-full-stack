import type { ReactNode } from "react"

type Heading1Props = {
  children: ReactNode;
}

export default function Heading1({children}: Heading1Props) {
  return (
    <h1 className="text-white text-2xl tracking-wide pb-2">{children}</h1>
  )
}