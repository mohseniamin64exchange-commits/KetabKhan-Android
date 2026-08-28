import { useState, useEffect } from 'react'
import StatusBar from '../components/StatusBar'
import NavBar from '../components/NavBar'
import { IcChevronLeft } from '../components/Icons'
import type { NavProps } from '../types'

const STAGES = [
  'بررسی فایل',
  'استخراج متن',
  'تشخیص تصاویر و پاورقی‌ها',
  'تشخیص فصل‌ها',
  'اصلاح ساختار',
  'آماده‌سازی پیش‌نمایش',
]

export default function Conversion({ navigate, goBack }: NavProps) {
  const [progress, setProgress] = useState(38)
  const [stage, setStage] = useState(1)
  const [done, setDone] = useState(false)

  useEffect(() => {
    const interval = setInterval(() => {
      setProgress(p => {
        const next = Math.min(p + 2, 100)
        if (next >= 100) {
          clearInterval(interval)
          setDone(true)
        }
        setStage(Math.min(Math.floor(next / 17), 5))
        return next
      })
    }, 200)
    return () => clearInterval(interval)
  }, [])

  return (
    <div className="flex flex-col h-full bg-[#F7F3ED]" dir="rtl">
      <StatusBar />

      <div className="flex items-center px-2 h-14 shrink-0 gap-1">
        <button onClick={goBack} className="w-12 h-12 flex items-center justify-center rounded-full text-[#1A1A18]">
          <IcChevronLeft size={24} />
        </button>
        <h1 className="text-[18px] font-bold text-[#1A1A18] flex-1 text-center pr-10">
          {done ? 'ساخت کتاب کامل شد' : 'در حال ساخت کتاب'}
        </h1>
      </div>

      <div className="flex-1 flex flex-col px-5 pt-6 gap-8">
        {/* Book name */}
        <div className="flex flex-col items-center gap-1">
          <div
            className="w-16 h-20 rounded-xl shadow-md"
            style={{ background: 'linear-gradient(160deg, #3D5A47 0%, #6B8F71 100%)' }}
          />
          <p className="text-[16px] font-bold text-[#1A1A18] mt-2">جزیره سرگردانی</p>
          <p className="text-[13px] text-[#6B6358]">سیمین دانشور</p>
        </div>

        {/* Progress */}
        <div className="flex flex-col gap-3">
          <div className="flex items-center justify-between">
            <span className="text-[13px] text-[#6B6358]">{done ? 'تکمیل شد' : STAGES[stage]}</span>
            <span className="text-[20px] font-bold text-[#2B5329]">{progress}٪</span>
          </div>
          <div className="h-2.5 bg-[#EDE8E0] rounded-full overflow-hidden">
            <div
              className="h-full bg-[#2B5329] rounded-full transition-all duration-200"
              style={{ width: `${progress}%` }}
            />
          </div>

          {/* Stage list */}
          <div className="mt-2 flex flex-col gap-1.5">
            {STAGES.map((s, i) => {
              const isPast = i < stage
              const isCurrent = i === stage && !done
              const isDoneStage = done || i < stage
              return (
                <div key={i} className="flex items-center gap-2.5">
                  <div
                    className="w-5 h-5 rounded-full flex items-center justify-center shrink-0"
                    style={{
                      background: isDoneStage ? '#2B5329' : isCurrent ? '#EBF2EB' : '#EDE8E0',
                      border: isCurrent ? '2px solid #2B5329' : 'none',
                    }}
                  >
                    {isDoneStage ? (
                      <svg width="10" height="8" viewBox="0 0 10 8" fill="none">
                        <path d="M1 4L3.5 6.5L9 1" stroke="white" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round" />
                      </svg>
                    ) : isCurrent ? (
                      <div className="w-2 h-2 rounded-full bg-[#2B5329] animate-pulse" />
                    ) : null}
                  </div>
                  <span
                    className="text-[13px]"
                    style={{
                      color: isDoneStage ? '#2B5329' : isCurrent ? '#1A1A18' : '#B0A898',
                      fontWeight: isCurrent ? 600 : 400,
                    }}
                  >
                    {s}
                  </span>
                </div>
              )
            })}
          </div>
        </div>

        {/* Info note */}
        {!done && (
          <div className="bg-[#FDFBF8] rounded-xl border border-[#EDE8E0] p-4">
            <p className="text-[13px] text-[#6B6358] leading-relaxed">
              می‌توانید از این صفحه خارج شوید؛ پردازش در پس‌زمینه ادامه پیدا می‌کند.
            </p>
          </div>
        )}
      </div>

      {/* Bottom actions */}
      <div className="px-5 pb-2 flex flex-col gap-2 shrink-0">
        {done ? (
          <button
            onClick={() => navigate('book-details')}
            className="w-full h-12 bg-[#2B5329] text-[#F7F3ED] rounded-xl text-[15px] font-semibold active:bg-[#3D7338]"
          >
            تکمیل مشخصات کتاب
          </button>
        ) : (
          <>
            <button
              onClick={() => navigate('library-grid')}
              className="w-full h-12 bg-[#FDFBF8] border border-[#DDD6CC] text-[#1A1A18] rounded-xl text-[15px] font-medium active:bg-[#EDE8E0]"
            >
              رفتن به کتابخانه
            </button>
            <button className="w-full h-12 text-[#A84040] text-[13px] active:opacity-70">
              لغو پردازش
            </button>
          </>
        )}
      </div>
      <NavBar />
    </div>
  )
}
