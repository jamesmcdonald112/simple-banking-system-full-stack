import { IoNotificationsOutline, IoPersonCircleSharp } from "react-icons/io5";


type WelcomeHeaderProps = {
  name: string;

}

export default function WelcomeHeader({name}: WelcomeHeaderProps) {

  return (
    <div className="flex flex-row items-center justify-between p-4 w-full">
      {/* Left Section: Avatar + Name */}
      <div className="flex flex-row items-center gap-3">
        <div className="bg-white/10 p-1 rounded-full">
          <IoPersonCircleSharp className="text-white text-5xl" />
        </div>
        <div className="flex flex-col leading-tight">
          <p className="text-white/70 text-sm">Welcome back,</p>
          <p className="text-white text-lg font-semibold">{name}</p>
        </div>
      </div>

      {/* Right Section: Notifications */}
      <button className="text-white/80 hover:text-white transition-colors">
        <IoNotificationsOutline size={24} />
      </button>
    </div>
  )
}