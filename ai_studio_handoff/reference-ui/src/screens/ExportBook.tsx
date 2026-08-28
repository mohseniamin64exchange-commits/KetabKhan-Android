import { useState } from 'react'
import StatusBar from '../components/StatusBar'
import { IcChevronLeft, IcCheck, IcShare } from '../components/Icons'
import type { NavProps } from '../types'

const CHECKS = [
  'ساختار کتاب سالم است',
  'فهرست مطالب بررسی شد',
  'تصاویر و پاورقی‌ها در دسترس‌اند',
  'فایل آماده اشتراک‌گذاری است',
]

export default function ExportBook({ navigate, goBack }: NavProps) {
  const [exported, setExported] = useState(false)

  return (
    <div className="flex flex-col h-full bg-[#F7F3ED]" dir="rtl">
      <StatusBar />

      <div className="flex items-center px-2 h-14 shrink-0">
        <button onClick={goBack} className="w-12 h-12 flex items-center justify-center rounded-full text-[#1A1A18]">
          <IcChevronLeft size={24} />
        </button>
        <h1 className="text-[18px] font-bold text-[#1A1A18] flex-1 text-center pr-10">ساخت فایل کتاب</h1>
      </div>

      <div className="flex-1 overflow-y-auto px-5 pb-4">
        {/* Book info */}
        <div className="flex items-center gap-4 bg-[#FDFBF8] rounded-2xl border border-[#DDD6CC] p-4 mb-5">
          <div
            className="w-14 h-18 rounded-xl shadow-sm shrink-0"
            style={{ background: 'linear-gradient(160deg, #3D5A47 0%, #6B8F71 100%)', height: 72 }}
          />
          <div>
            <p className="text-[15px] font-bold text-[#1A1A18]">جزیره سرگردانی</p>
            <p className="text-[13px] text-[#6B6358]">سیمین دانشور</p>
          </div>
        </div>

        {/* Explanation */}
        <div className="bg-[#EBF2EB] rounded-2xl p-4 mb-5">
          <p className="text-[13px] text-[#2B5329] leading-relaxed font-medium">محتوای فایل کتاب شامل:</p>
          <ul className="mt-2 flex flex-col gap-1">
            {['جلد و مشخصات', 'مقدمه و متن کامل', 'فصل‌ها و زیرفصل‌ها', 'تصاویر، جداول و پاورقی‌ها', 'فهرست مطالب'].map((item, i) => (
              <li key={i} className="flex items-center gap-2 text-[13px] text-[#2B5329]">
                <div className="w-1.5 h-1.5 rounded-full bg-[#2B5329]" />
                {item}
              </li>
            ))}
          </ul>
          <p className="text-[12px] text-[#6B6358] mt-3 pt-3 border-t border-[#C8D8C8]">
            موقعیت مطالعه، نشانک‌ها و تنظیمات شخصی در این فایل ذخیره نمی‌شوند.
          </p>
        </div>

        {/* Validation checklist */}
        <p className="text-[12px] font-semibold text-[#6B6358] mb-3">بررسی سلامت فایل</p>
        <div className="bg-[#FDFBF8] rounded-2xl border border-[#DDD6CC] overflow-hidden mb-5">
          {CHECKS.map((check, i) => (
            <div key={i} className="flex items-center gap-3 px-5 py-3.5 border-b border-[#EDE8E0] last:border-b-0">
              <div className="w-5 h-5 rounded-full bg-[#2B5329] flex items-center justify-center shrink-0">
                <IcCheck size={11} className="text-white" />
              </div>
              <p className="text-[14px] text-[#1A1A18]">{check}</p>
            </div>
          ))}
        </div>

        {/* Success state */}
        {exported && (
          <div className="bg-[#EBF2EB] border border-[#A8C8A8] rounded-2xl p-4 mb-4">
            <div className="flex items-center gap-3 mb-2">
              <div className="w-12 h-12 rounded-full bg-[#2B5329] flex items-center justify-center">
                <IcCheck size={16} className="text-white" />
              </div>
              <p className="text-[15px] font-bold text-[#2B5329]">فایل کتاب آماده شد</p>
            </div>
            <div className="bg-[#FDFBF8] rounded-xl px-4 py-3">
              <p className="text-[13px] font-mono text-[#1A1A18]">جزیره-سرگردانی.bookapp</p>
              <p className="text-[12px] text-[#6B6358] mt-0.5">۲.۱ مگابایت · پسوند موقت</p>
            </div>
          </div>
        )}
      </div>

      <div className="px-5 pb-2 flex flex-col gap-2 shrink-0">
        {!exported ? (
          <button
            onClick={() => setExported(true)}
            className="w-full h-12 bg-[#2B5329] text-[#F7F3ED] rounded-xl text-[15px] font-semibold active:bg-[#3D7338]"
          >
            ساخت فایل کتاب
          </button>
        ) : (
          <>
            <button className="w-full h-12 bg-[#2B5329] text-[#F7F3ED] rounded-xl text-[15px] font-semibold flex items-center justify-center gap-2 active:bg-[#3D7338]">
              <IcShare size={20} />
              اشتراک‌گذاری
            </button>
            <button className="w-full h-12 bg-[#FDFBF8] border border-[#DDD6CC] text-[#1A1A18] rounded-xl text-[14px] font-medium active:bg-[#EDE8E0]">
              ذخیره در گوشی
            </button>
          </>
        )}
      </div>
      <div className="h-6" />
    </div>
  )
}
