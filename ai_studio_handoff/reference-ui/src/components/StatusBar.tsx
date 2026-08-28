interface StatusBarProps {
  dark?: boolean
  time?: string
}

export default function StatusBar({ dark = false, time = '۱۱:۳۰' }: StatusBarProps) {
  const color = dark ? 'text-white/80' : 'text-[#1A1A18]/60'
  return (
    <div
      className={`flex items-center justify-between px-6 h-6 text-[12px] font-medium select-none shrink-0 ${color}`}
      style={{ fontFamily: 'system-ui, sans-serif' }}
    >
      <span>{time}</span>
      <div className="flex items-center gap-1">
        <svg width="16" height="10" viewBox="0 0 16 10" fill="currentColor">
          <rect x="0" y="4" width="3" height="6" rx="0.5" opacity="0.4" />
          <rect x="4" y="2.5" width="3" height="7.5" rx="0.5" opacity="0.6" />
          <rect x="8" y="1" width="3" height="9" rx="0.5" opacity="0.8" />
          <rect x="12" y="0" width="3" height="10" rx="0.5" />
        </svg>
        <svg width="15" height="10" viewBox="0 0 15 10" fill="currentColor">
          <path d="M7.5 2.5C9.5 2.5 11.3 3.3 12.6 4.6L14 3.2C12.3 1.5 10 0.5 7.5 0.5C5 0.5 2.7 1.5 1 3.2L2.4 4.6C3.7 3.3 5.5 2.5 7.5 2.5Z" opacity="0.5" />
          <path d="M7.5 5.5C8.7 5.5 9.8 6 10.6 6.8L12 5.4C10.8 4.3 9.2 3.5 7.5 3.5C5.8 3.5 4.2 4.2 3 5.4L4.4 6.8C5.2 6 6.3 5.5 7.5 5.5Z" opacity="0.7" />
          <circle cx="7.5" cy="9" r="1.5" />
        </svg>
        <svg width="25" height="11" viewBox="0 0 25 11" fill="none">
          <rect x="0.5" y="0.5" width="21" height="10" rx="2.5" stroke="currentColor" strokeOpacity="0.35" />
          <rect x="2" y="2" width="16" height="7" rx="1.5" fill="currentColor" />
          <path d="M23 3.5v4a1.5 1.5 0 0 0 0-4z" fill="currentColor" opacity="0.4" />
        </svg>
      </div>
    </div>
  )
}
