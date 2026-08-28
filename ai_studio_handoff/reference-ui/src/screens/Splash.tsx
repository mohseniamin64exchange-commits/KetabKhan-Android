import { useEffect } from 'react'
import type { NavProps } from '../types'

export default function Splash({ navigate }: NavProps) {
  useEffect(() => {
    const t = setTimeout(() => navigate('library-empty'), 2200)
    return () => clearTimeout(t)
  }, [navigate])

  return (
    <div className="flex flex-col items-center justify-center h-full bg-[#F7F3ED]">
      <div className="flex flex-col items-center gap-6">
        <div
          className="w-20 h-20 rounded-2xl flex items-center justify-center"
          style={{ background: '#2B5329' }}
        >
          <svg width="44" height="44" viewBox="0 0 44 44" fill="none">
            <path
              d="M8 36V10a2 2 0 0 1 2-2h16l10 10v18a2 2 0 0 1-2 2H10a2 2 0 0 1-2-2z"
              fill="none"
              stroke="#F7F3ED"
              strokeWidth="2"
              strokeLinecap="round"
              strokeLinejoin="round"
            />
            <path d="M26 8v10h10" stroke="#F7F3ED" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" />
            <path d="M15 24h14M15 29h10" stroke="#F7F3ED" strokeWidth="1.5" strokeLinecap="round" />
          </svg>
        </div>
        <div className="flex flex-col items-center gap-1">
          <h1
            className="text-[28px] font-bold tracking-tight"
            style={{ color: '#2B5329', fontFamily: 'Vazirmatn, Tahoma, sans-serif' }}
          >
            کتاب‌خوان
          </h1>
          <p className="text-[13px] text-[#6B6358]" style={{ fontFamily: 'Vazirmatn, Tahoma, sans-serif' }}>
            کتابخانه شخصی شما
          </p>
        </div>
      </div>
      <div className="absolute bottom-16 flex flex-col items-center gap-2">
        <div className="w-6 h-1 rounded-full bg-[#2B5329]/20 animate-pulse" />
      </div>
    </div>
  )
}
