interface NavBarProps {
  dark?: boolean
  onBack?: () => void
}

export default function NavBar({ dark = false, onBack }: NavBarProps) {
  const color = dark ? 'bg-transparent' : 'bg-transparent'
  const pillColor = dark ? 'bg-white/30' : 'bg-[#1A1A18]/20'
  return (
    <div className={`flex items-center justify-center h-12 shrink-0 ${color}`}>
      {onBack ? (
        <button onClick={onBack} className="w-12 h-1 rounded-full" style={{ background: dark ? 'rgba(255,255,255,0.3)' : 'rgba(26,26,24,0.2)' }} />
      ) : (
        <div className={`w-32 h-1 rounded-full ${pillColor}`} />
      )}
    </div>
  )
}
