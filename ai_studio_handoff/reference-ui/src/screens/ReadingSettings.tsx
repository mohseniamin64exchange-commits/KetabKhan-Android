import { useState } from 'react'
import StatusBar from '../components/StatusBar'
import { IcChevronLeft, IcMoon } from '../components/Icons'
import type { NavProps } from '../types'

function Slider({ label, leftIcon, rightIcon }: { label?: string; leftIcon?: string; rightIcon?: string }) {
  const [val, setVal] = useState(50)
  return (
    <div className="flex items-center gap-3">
      {leftIcon && <span className="text-[13px] text-[#6B6358] shrink-0">{leftIcon}</span>}
      {label && <span className="text-[13px] text-[#6B6358] shrink-0 w-20">{label}</span>}
      <div className="flex-1 relative h-5 flex items-center">
        <div className="w-full h-1.5 bg-[#EDE8E0] rounded-full" />
        <div
          className="absolute right-0 h-1.5 bg-[#2B5329] rounded-full"
          style={{ width: `${val}%` }}
        />
        <input
          type="range" min="0" max="100" value={val}
          onChange={e => setVal(+e.target.value)}
          className="absolute inset-0 w-full opacity-0 cursor-pointer"
        />
        <div
          className="absolute top-1/2 -translate-y-1/2 w-4 h-4 rounded-full bg-[#2B5329] shadow border-2 border-white"
          style={{ right: `calc(${val}% - 8px)` }}
        />
      </div>
      {rightIcon && <span className="text-[17px] text-[#6B6358] shrink-0">{rightIcon}</span>}
    </div>
  )
}

const THEMES = [
  { label: 'روشن', bg: '#FAF6F0', text: '#1A1A18' },
  { label: 'کرم', bg: '#F2E8D5', text: '#3A2E22' },
  { label: 'تیره', bg: '#1E2020', text: '#D8D4CC' },
]

const FONTS = ['وزیرمتن', 'نوتو نسخ', 'نوتو سنس']

export default function ReadingSettings({ navigate, goBack }: NavProps) {
  const [nightMode, setNightMode] = useState(false)
  const [activeTheme, setActiveTheme] = useState(0)
  const [navMode, setNavMode] = useState<'scroll' | 'page'>('scroll')
  const [screenOn, setScreenOn] = useState(false)
  const [align, setAlign] = useState<'right' | 'justify'>('justify')
  const [activeFont, setActiveFont] = useState(0)

  return (
    <div className="flex flex-col h-full bg-[#F7F3ED]" dir="rtl">
      <StatusBar />

      <div className="flex items-center px-2 h-14 shrink-0 border-b border-[#EDE8E0]">
        <button onClick={goBack} className="w-12 h-12 flex items-center justify-center rounded-full text-[#1A1A18]">
          <IcChevronLeft size={24} />
        </button>
        <h1 className="text-[18px] font-bold text-[#1A1A18] flex-1 text-center pr-10">تنظیمات مطالعه</h1>
      </div>

      <div className="flex-1 overflow-y-auto px-5 py-4 flex flex-col gap-5">
        {/* Font family */}
        <div>
          <p className="text-[12px] font-semibold text-[#6B6358] mb-2">قلم</p>
          <div className="flex gap-2">
            {FONTS.map((f, i) => (
              <button
                key={i}
                onClick={() => setActiveFont(i)}
                className="flex-1 h-12 rounded-xl border text-[13px] font-medium transition-colors"
                style={{
                  background: activeFont === i ? '#EBF2EB' : '#FDFBF8',
                  borderColor: activeFont === i ? '#2B5329' : '#DDD6CC',
                  color: activeFont === i ? '#2B5329' : '#6B6358',
                  fontFamily: 'Vazirmatn, Tahoma, sans-serif',
                }}
              >
                {f}
              </button>
            ))}
          </div>
        </div>

        {/* Font size */}
        <div>
          <p className="text-[12px] font-semibold text-[#6B6358] mb-2">اندازه قلم</p>
          <Slider leftIcon="الف" rightIcon="الف" />
        </div>

        {/* Line spacing */}
        <div>
          <p className="text-[12px] font-semibold text-[#6B6358] mb-2">فاصله خطوط</p>
          <Slider />
        </div>

        {/* Text alignment */}
        <div>
          <p className="text-[12px] font-semibold text-[#6B6358] mb-2">تراز متن</p>
          <div className="flex gap-2">
            {(['justify', 'right'] as const).map((a) => (
              <button
                key={a}
                onClick={() => setAlign(a)}
                className="flex-1 h-12 rounded-xl border text-[13px] font-medium"
                style={{
                  background: align === a ? '#EBF2EB' : '#FDFBF8',
                  borderColor: align === a ? '#2B5329' : '#DDD6CC',
                  color: align === a ? '#2B5329' : '#6B6358',
                }}
              >
                {a === 'justify' ? 'دوطرفه' : 'راست‌چین'}
              </button>
            ))}
          </div>
        </div>

        {/* Navigation */}
        <div>
          <p className="text-[12px] font-semibold text-[#6B6358] mb-2">نوع پیمایش</p>
          <div className="flex gap-2">
            {(['scroll', 'page'] as const).map(m => (
              <button
                key={m}
                onClick={() => setNavMode(m)}
                className="flex-1 h-12 rounded-xl border text-[13px] font-medium"
                style={{
                  background: navMode === m ? '#EBF2EB' : '#FDFBF8',
                  borderColor: navMode === m ? '#2B5329' : '#DDD6CC',
                  color: navMode === m ? '#2B5329' : '#6B6358',
                }}
              >
                {m === 'scroll' ? 'پیمایش عمودی' : 'ورق‌زدن'}
              </button>
            ))}
          </div>
        </div>

        {/* Screen on toggle */}
        <div className="flex items-center justify-between">
          <p className="text-[14px] font-medium text-[#1A1A18]">روشن نگه‌داشتن صفحه</p>
          <button
            onClick={() => setScreenOn(v => !v)}
            className={`w-12 h-7 rounded-full transition-colors relative ${screenOn ? 'bg-[#2B5329]' : 'bg-[#DDD6CC]'}`}
          >
            <div className={`w-5 h-5 rounded-full bg-white shadow-sm absolute top-1 transition-all ${screenOn ? 'right-1' : 'left-1'}`} />
          </button>
        </div>

        {/* Quick themes */}
        <div>
          <p className="text-[12px] font-semibold text-[#6B6358] mb-2">پوسته</p>
          <div className="flex gap-2">
            {THEMES.map((t, i) => (
              <button
                key={i}
                onClick={() => setActiveTheme(i)}
                className="flex-1 h-12 rounded-xl border-2 flex flex-col items-center justify-center gap-1"
                style={{
                  background: t.bg,
                  borderColor: activeTheme === i ? '#2B5329' : t.bg,
                }}
              >
                <span className="text-[12px] font-bold" style={{ color: t.text }}>{t.label}</span>
              </button>
            ))}
          </div>
        </div>

        {/* Night mode */}
        <div className="bg-[#FDFBF8] rounded-2xl border border-[#DDD6CC] p-4">
          <div className="flex items-center justify-between mb-3">
            <div className="flex items-center gap-2">
              <IcMoon size={18} className="text-[#6B6358]" />
              <p className="text-[14px] font-semibold text-[#1A1A18]">حالت مطالعه شبانه</p>
            </div>
            <button
              onClick={() => setNightMode(v => !v)}
              className={`w-12 h-7 rounded-full transition-colors relative ${nightMode ? 'bg-[#C8823A]' : 'bg-[#DDD6CC]'}`}
            >
              <div className={`w-5 h-5 rounded-full bg-white shadow-sm absolute top-1 transition-all ${nightMode ? 'right-1' : 'left-1'}`} />
            </button>
          </div>
          {nightMode && (
            <>
              <p className="text-[12px] text-[#6B6358] mb-3 leading-relaxed">
                نور گرم‌تر با کاهش آبی صفحه برای راحتی بیشتر در شب.
              </p>

              {/* Night preview */}
              <div className="rounded-xl p-3 mb-4" style={{ background: '#1C1A16' }}>
                <p className="text-[12px] leading-relaxed" style={{ color: '#E8D9BE', fontFamily: 'Vazirmatn, Tahoma, sans-serif', direction: 'rtl' }}>
                  داستان از آنجا آغاز می‌شود که مهری با چمدانی کوچک...
                </p>
              </div>

              <div className="flex flex-col gap-3">
                <div>
                  <p className="text-[12px] text-[#6B6358] mb-1.5">روشنایی</p>
                  <Slider leftIcon="کم" rightIcon="زیاد" />
                </div>
                <div>
                  <p className="text-[12px] text-[#6B6358] mb-1.5">گرمی رنگ</p>
                  <Slider leftIcon="سرد" rightIcon="گرم" />
                </div>
              </div>
            </>
          )}
        </div>
      </div>
    </div>
  )
}
