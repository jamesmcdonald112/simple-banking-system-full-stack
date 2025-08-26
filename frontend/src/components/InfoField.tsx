import type { ReactNode } from "react";

type InfoFieldProps = {
  label: string;
  icon?: ReactNode;
  info: string;
};

export default function InfoField({ label, icon, info }: InfoFieldProps) {
  return (
    <div className="flex flex-col gap-2 w-full">
      <label className="label text-muted">{label}</label>

      <div className="input-icon-wrapper cursor-default">
        {icon && <div className="icon-muted">{icon}</div>}
        <p className="text-white">{info}</p>
      </div>
    </div>
  );
}