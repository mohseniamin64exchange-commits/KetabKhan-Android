import { useState } from 'react'
import StatusBar from '../components/StatusBar'
import { IcBookmark, IcSearch, IcMore, IcToc, IcSettings, IcChevronLeft } from '../components/Icons'
import type { NavProps } from '../types'

const READING_CONTENT = `داستان از آنجا آغاز می‌شود که مهری با چمدانی کوچک و دلی پر از تردید به شهر بازمی‌گردد. کوچه‌های آشنا حالا غریبه به نظر می‌رسند. صدای قدم‌هایش روی سنگ‌فرش‌های قدیمی طنین می‌اندازد — همان صدایی که سال‌ها پیش نیز اینجا شنیده می‌شد.

هنوز بوی گل‌های یاس پشت حیاط خانه قدیمی را حس می‌کند. مادر همیشه می‌گفت که گل‌های یاس خاطره را نگه می‌دارند. شاید به همین خاطر است که هر بار که آن بو را حس می‌کند، همه چیز برمی‌گردد.

پنجره خانه همسایه هنوز با پرده‌های آبی آراسته است. مردی پیر در حیاط نشسته و کتاب می‌خواند. همان مرد. همان صندلی. انگار زمان در این کوچه ایستاده است.`

export default function Reader({ navigate, goBack, initialControls = false }: NavProps & { initialControls?: boolean }) {
  const [showControls, setShowControls] = useState(initialControls)
  const [showFootnote, setShowFootnote] = useState(false)

  return (
    <div
      className="flex flex-col h-full"
      style={{ background: '#FAF6F0' }}
      dir="rtl"
    >
      {/* Status bar — only visible when controls shown */}
      {showControls && <StatusBar />}
      {!showControls && <div className="h-6" />}

      {/* Top controls */}
      {showControls && (
        <div
          className="flex items-center px-2 h-14 shrink-0 border-b border-[#EDE8E0]"
          style={{ background: '#FAF6F0' }}
        >
          <button onClick={goBack} className="w-12 h-12 flex items-center justify-center rounded-full text-[#1A1A18]">
            <IcChevronLeft size={22} />
          </button>
          <p className="flex-1 text-center text-[14px] font-semibold text-[#1A1A18] pr-6 line-clamp-1">
            فصل اول: آشنایی با موضوع
          </p>
          <button className="w-12 h-12 flex items-center justify-center rounded-full text-[#6B6358]">
            <IcSearch size={20} />
          </button>
          <button className="w-12 h-12 flex items-center justify-center rounded-full text-[#6B6358]">
            <IcBookmark size={20} />
          </button>
          <button className="w-12 h-12 flex items-center justify-center rounded-full text-[#6B6358]">
            <IcMore size={20} />
          </button>
        </div>
      )}

      {/* Reading area */}
      <div
        className="flex-1 overflow-y-auto px-6 py-4"
        onClick={() => setShowControls(v => !v)}
      >
        {!showControls && (
          <p className="text-[12px] text-[#6B6358] mb-4 text-center opacity-50">ضربه بزنید برای کنترل‌ها</p>
        )}
        <h2 className="text-[18px] font-bold text-[#1A1A18] mb-5 leading-snug">
          فصل اول: آشنایی با موضوع
        </h2>
        <div
          className="text-[16px] text-[#1A1A18] leading-[2.1] text-justify"
          style={{ fontFamily: 'Vazirmatn, Tahoma, sans-serif' }}
        >
          {READING_CONTENT.split('\n\n').map((para, i) => (
            <p key={i} className="mb-5">{para}</p>
          ))}
        </div>

        {/* Image */}
        <div className="my-6">
          <div className="w-full h-40 rounded-xl bg-[#EDE8E0] flex items-center justify-center mb-2">
            <svg width="48" height="48" viewBox="0 0 48 48" fill="none">
              <rect x="4" y="4" width="40" height="40" rx="5" stroke="#B0A898" strokeWidth="1.8" />
              <circle cx="17" cy="18" r="5" stroke="#B0A898" strokeWidth="1.8" />
              <path d="M4 34l10-8 8 7 8-10 14 11" stroke="#B0A898" strokeWidth="1.8" strokeLinecap="round" />
            </svg>
          </div>
          <p className="text-[12px] text-[#6B6358] text-center">
            کوچه‌های قدیمی شهر در آغاز قرن بیستم
          </p>
        </div>

        <div
          className="text-[16px] text-[#1A1A18] leading-[2.1] text-justify"
          style={{ fontFamily: 'Vazirmatn, Tahoma, sans-serif' }}
        >
          <p className="mb-5">
            روزها می‌گذرند و مهری هر روز کمی بیشتر با این شهر آشتی می‌کند. خاطرات لایه‌به‌لایه باز می‌شوند، مثل کتاب قدیمی که کسی سال‌ها آن را نگشوده است.
            <button
              type="button"
              aria-label="نمایش پاورقی یک"
              onClick={(event) => { event.stopPropagation(); setShowFootnote(true) }}
              className="inline-flex min-w-12 h-12 items-center justify-center align-middle text-[12px] font-bold text-[#2B5329] rounded-full active:bg-[#EBF2EB]"
            >
              ۱
            </button>
          </p>
        </div>

        {/* Footnote */}
        <div className="border-t border-[#DDD6CC] pt-3 mb-6">
          <p className="text-[12px] text-[#6B6358] leading-relaxed">
            ¹ اشاره‌ای است به تکنیک روایی «جریان سیال ذهن» که در آثار جیمز جویس نیز دیده می‌شود.
          </p>
        </div>
      </div>

      {/* Bottom controls */}
      {showControls && (
        <div
          className="shrink-0 px-4 py-3 border-t border-[#EDE8E0]"
          style={{ background: '#FAF6F0' }}
        >
          {/* Progress slider */}
          <div className="flex items-center gap-3 mb-3">
            <span className="text-[12px] text-[#6B6358] shrink-0">۱۲٪</span>
            <div className="flex-1 h-1 bg-[#EDE8E0] rounded-full relative">
              <div className="absolute inset-y-0 right-0 bg-[#2B5329] rounded-full" style={{ width: '12%' }} />
              <div
                className="absolute top-1/2 -translate-y-1/2 w-4 h-4 rounded-full bg-[#2B5329] shadow-sm"
                style={{ right: 'calc(12% - 8px)' }}
              />
            </div>
            <span className="text-[12px] text-[#6B6358] shrink-0">۱۰۰٪</span>
          </div>
          {/* Chapter nav */}
          <div className="flex items-center justify-between">
            <button
              onClick={() => navigate('book-nav')}
              className="w-12 h-12 flex items-center justify-center rounded-full text-[#6B6358] active:bg-[#EDE8E0]"
            >
              <IcToc size={22} />
            </button>
            <button className="w-12 h-12 flex items-center justify-center rounded-full text-[#6B6358] active:bg-[#EDE8E0]">
              <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8">
                <path d="M19 12H5M12 5l7 7-7 7" strokeLinecap="round" strokeLinejoin="round" />
              </svg>
            </button>
            <p className="text-[13px] font-medium text-[#1A1A18]">فصل ۱ / ۱۸</p>
            <button className="w-12 h-12 flex items-center justify-center rounded-full text-[#6B6358] active:bg-[#EDE8E0]">
              <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8">
                <path d="M5 12h14M12 19l7-7-7-7" strokeLinecap="round" strokeLinejoin="round" />
              </svg>
            </button>
            <button
              onClick={() => navigate('reading-settings')}
              className="w-12 h-12 flex items-center justify-center rounded-full text-[#6B6358] active:bg-[#EDE8E0]"
            >
              <IcSettings size={22} />
            </button>
          </div>
        </div>
      )}
      {!showControls && <div className="h-6" />}

      {showFootnote && (
        <div className="absolute inset-0 z-50 flex items-end" onClick={() => setShowFootnote(false)}>
          <div className="absolute inset-0 bg-black/35" />
          <div
            className="relative w-full rounded-t-2xl bg-[#FDFBF8] px-5 pb-6 pt-3"
            onClick={(event) => event.stopPropagation()}
          >
            <div className="w-12 h-1 rounded-full bg-[#DDD6CC] mx-auto mb-4" />
            <p className="text-[15px] font-bold text-[#1A1A18] mb-2">پاورقی ۱</p>
            <p className="text-[14px] leading-7 text-[#6B6358] mb-4">
              اشاره‌ای است به تکنیک روایی «جریان سیال ذهن» که در آثار جیمز جویس نیز دیده می‌شود.
            </p>
            <button
              type="button"
              onClick={() => setShowFootnote(false)}
              className="w-full h-12 rounded-xl bg-[#2B5329] text-white text-[14px] font-semibold"
            >
              بستن
            </button>
          </div>
        </div>
      )}
    </div>
  )
}
