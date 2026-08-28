import { useState } from 'react'
import StatusBar from '../components/StatusBar'
import NavBar from '../components/NavBar'
import { IcFile, IcChevronLeft, IcX, IcScan, IcTable } from '../components/Icons'
import type { NavProps } from '../types'

export default function SelectPDF({ navigate, goBack }: NavProps) {
  const [selected, setSelected] = useState(false)
  const [onlineToggle, setOnlineToggle] = useState(false)

  return (
    <div className="flex flex-col h-full bg-[#F7F3ED]" dir="rtl">
      <StatusBar />

      {/* Top Bar */}
      <div className="flex items-center px-2 h-14 shrink-0 gap-1">
        <button onClick={goBack} className="w-12 h-12 flex items-center justify-center rounded-full text-[#1A1A18]">
          <IcChevronLeft size={24} />
        </button>
        <h1 className="text-[18px] font-bold text-[#1A1A18] flex-1 text-center pr-10">
          ساخت کتاب جدید
        </h1>
      </div>

      <div className="flex-1 overflow-y-auto px-5 pb-4">
        {!selected ? (
          <>
            <p className="text-[14px] text-[#6B6358] leading-relaxed mb-6 mt-2">
              فایل PDF کتاب خود را انتخاب کنید. این برنامه از PDF‌های متنی، اسکن‌شده، و ترکیبی پشتیبانی می‌کند.
            </p>

            {/* File Drop Area */}
            <button
              onClick={() => setSelected(true)}
              className="w-full border-2 border-dashed border-[#DDD6CC] rounded-2xl p-8 flex flex-col items-center gap-4 active:bg-[#EDE8E0] transition-colors"
            >
              <div className="w-16 h-16 rounded-2xl bg-[#EBF2EB] flex items-center justify-center">
                <IcFile size={32} className="text-[#2B5329]" />
              </div>
              <div className="flex flex-col items-center gap-1 text-center">
                <p className="text-[15px] font-semibold text-[#2B5329]">انتخاب فایل PDF</p>
                <p className="text-[12px] text-[#6B6358]">ضربه بزنید تا مدیریت فایل باز شود</p>
              </div>
            </button>

            {/* Info Cards */}
            <div className="mt-5 flex flex-col gap-3">
              {[
                { Icon: IcFile, label: 'PDF متنی', desc: 'بهترین کیفیت تبدیل' },
                { Icon: IcScan, label: 'PDF اسکن‌شده', desc: 'نیاز به تشخیص متن (OCR)' },
                { Icon: IcTable, label: 'PDF ترکیبی', desc: 'ترکیب متن و تصویر' },
              ].map(item => (
                <div key={item.label} className="flex items-center gap-3 bg-[#FDFBF8] rounded-xl px-4 py-3 border border-[#EDE8E0]">
                  <div className="w-12 h-12 rounded-xl bg-[#EBF2EB] flex items-center justify-center shrink-0">
                    <item.Icon size={22} className="text-[#2B5329]" />
                  </div>
                  <div>
                    <p className="text-[14px] font-semibold text-[#1A1A18]">{item.label}</p>
                    <p className="text-[12px] text-[#6B6358]">{item.desc}</p>
                  </div>
                </div>
              ))}
            </div>
          </>
        ) : (
          <>
            {/* Selected File Card */}
            <div className="bg-[#FDFBF8] rounded-2xl border border-[#DDD6CC] p-4 mt-2 shadow-sm">
              <div className="flex items-start gap-3">
                <div className="w-12 h-12 rounded-xl bg-[#EBF2EB] flex items-center justify-center shrink-0">
                  <IcFile size={24} className="text-[#2B5329]" />
                </div>
                <div className="flex-1 min-w-0">
                  <p className="text-[14px] font-semibold text-[#1A1A18] line-clamp-1">جزیره_سرگردانی.pdf</p>
                  <div className="flex gap-3 mt-1 flex-wrap">
                    <span className="text-[12px] text-[#6B6358]">۳.۲ مگابایت</span>
                    <span className="text-[12px] text-[#6B6358]">۳۱۴ صفحه</span>
                    <span className="text-[12px] text-[#6B6358]">متنی</span>
                  </div>
                  <div className="flex items-center gap-1 mt-1">
                    <span className="text-[12px] font-medium text-[#2B5329]">فارسی</span>
                    <span className="text-[12px] text-[#DDD6CC]">·</span>
                    <span className="text-[12px] text-[#6B6358]">راست به چپ</span>
                  </div>
                </div>
                <button
                  onClick={() => setSelected(false)}
                  className="w-12 h-12 flex items-center justify-center rounded-full text-[#6B6358] active:bg-[#EDE8E0]"
                >
                  <IcX size={18} />
                </button>
              </div>
            </div>

            {/* Online Toggle */}
            <div className="mt-5 bg-[#FDFBF8] rounded-2xl border border-[#DDD6CC] p-4">
              <div className="flex items-center justify-between">
                <div className="flex flex-col gap-0.5">
                  <p className="text-[14px] font-semibold text-[#1A1A18]">بهبود پردازش با اینترنت</p>
                  <p className="text-[12px] text-[#6B6358]">اختیاری</p>
                </div>
                <button
                  onClick={() => setOnlineToggle(v => !v)}
                  className={`w-12 h-7 rounded-full transition-colors relative ${onlineToggle ? 'bg-[#2B5329]' : 'bg-[#DDD6CC]'}`}
                >
                  <div
                    className={`w-5 h-5 rounded-full bg-white shadow-sm absolute top-1 transition-all ${onlineToggle ? 'right-1' : 'left-1'}`}
                  />
                </button>
              </div>
              {onlineToggle && (
                <p className="text-[12px] text-[#6B6358] mt-3 leading-relaxed bg-[#F7F3ED] rounded-xl p-3">
                  در صورت نیاز، فقط بخش‌های دشوار برای اصلاح بهتر پردازش می‌شوند. محتوای کامل کتاب منتقل نمی‌شود.
                </p>
              )}
            </div>
          </>
        )}
      </div>

      {/* Bottom Action */}
      {selected && (
        <div className="px-5 pb-2 shrink-0">
          <button
            onClick={() => navigate('conversion')}
            className="w-full h-12 bg-[#2B5329] text-[#F7F3ED] rounded-xl text-[15px] font-semibold active:bg-[#3D7338]"
          >
            شروع تبدیل
          </button>
        </div>
      )}
      <NavBar />
    </div>
  )
}
