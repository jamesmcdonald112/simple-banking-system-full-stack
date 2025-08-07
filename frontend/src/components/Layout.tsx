import { Outlet } from "react-router-dom";
import Navbar from "./Navbar";
import Footer from "./Footer";

export function Layout() {
  return (
    <div className="bg-[#161622] min-h-screen flex flex-col">
      <Navbar />
      <main className="flex-grow flex items-center justify-center">
        <Outlet />
      </main>
      <Footer />
    </div>
  )
}