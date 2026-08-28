import { useState } from 'react'
import StatusBar from '../components/StatusBar'
import NavBar from '../components/NavBar'
import { IcChevronLeft, IcCheck, IcWarning, IcImport } from '../components/Icons'
import type { NavProps } from '../types'

type State = 'empty' | 'selected' | 'duplicate' | 'invalid' | 'success'

export default function ImportBook({ navigate, goBack }: NavProps) {
  const [state, setState] = useState<State>('empty')

  return (
    <div className="flex flex-col h-full bg-[#F7F3ED]" dir="rtl">
      <StatusBar />

      <div className="flex items-center px-2 h-14 shrink-0">
        <button onClick={goBack} className="w-12 h-12 flex items-center justify-center rounded-full text-[#1A1A18]">
          <IcChevronLeft size={24} />
        </button>
        <h1 className="text-[18px] font-bold text-[#1A1A18] flex-1 text-center pr-10">واردکردن کتاب</h1>
      </div>

      <div className="flex-1 overflow-y-auto px-5 pb-4">
        {state === 'empty' && (
          <div className="flex flex-col items-center justify-center min-h-60 gap-5">
            <div className="w-24 h-24 rounded-3xl bg-[#EBF2EB] flex items-center justify-center">
              <IcImport size={40} className="text-[#2B5329]" />
            </div>
            <p className="text-[14px] text-[#6B6358] text-center leading-relaxed">
              فایل کتاب با پسوند .bookapp را انتخاب کنید تا به کتابخانه اضافه شود.
            </p>
            <button
              onClick={() => setState('selected')}
              className="h-12 px-6 bg-[#2B5329] text-[#F7F3ED] rounded-xl text-[15px] font-semibold flex items-center gap-2 active:bg-[#3D7338]"
            >
              <IcImport size={20} />
              انتخاب فایل کتاب
            </button>

            {/* Test other states */}
            <div className="flex gap-2 flex-wrap justify-center mt-2">
              <button onClick={() => setState('invalid')} className="min-h-12 text-[12px] text-[#6B6358] border border-[#DDD6CC] rounded-lg px-3 py-2">فایل نامعتبر</button>
              <button onClick={() => setState('success')} className="min-h-12 text-[12px] text-[#6B6358] border border-[#DDD6CC] rounded-lg px-3 py-2">موفق</button>
            </div>
          </div>
        )}

        {state === 'selected' && (
          <>
            {/* File preview card */}
            <div className="bg-[#FDFBF8] rounded-2xl border border-[#DDD6CC] p-5 mt-2">
              <div className="flex gap-4">
                <div
                  className="w-16 h-20 rounded-xl shadow-sm shrink-0"
                  style={{ background: 'linear-gradient(160deg, #5A3D3D 0%, #8F6B6B 100%)' }}
                />
                <div className="flex flex-col gap-1">
                  <p className="text-[15px] font-bold text-[#1A1A18]">صد سال تنهایی</p>
                  <p className="text-[13px] text-[#6B6358]">گابریل گارسیا مارکز</p>
                  <p className="text-[12px] text-[#6B6358]">مترجم: محمد مجلسی</p>
                </div>
              </div>
              <div className="mt-4 flex flex-col gap-2 border-t border-[#EDE8E0] pt-3">
                {[
                  ['حجم فایل', '۱.۸ مگابایت'],
                  ['تعداد فصل‌ها', '۲۰ فصل'],
                  ['نسخه فرمت', '۱.۲'],
                  ['سلامت فایل', '✅ سالم'],
                ].map(([k, v]) => (
                  <div key={k} className="flex justify-between">
                    <span className="text-[12px] text-[#6B6358]">{k}</span>
                    <span className="text-[12px] font-medium text-[#1A1A18]">{v}</span>
                  </div>
                ))}
              </div>
            </div>
          </>
        )}

        {state === 'duplicate' && (
          <div className="mt-2 bg-[#FDF3E7] border border-[#F0D8B0] rounded-2xl p-5">
            <div className="flex items-center gap-3 mb-3">
              <IcWarning size={22} className="text-[#B87A28]" />
              <p className="text-[15px] font-bold text-[#B87A28]">کتاب تکراری</p>
            </div>
            <p className="text-[13px] text-[#6B6358] leading-relaxed mb-4">
              این کتاب از قبل در کتابخانه وجود دارد. چه کاری می‌خواهید انجام دهید؟
            </p>
            <div className="flex flex-col gap-2">
              <button onClick={() => setState('success')} className="w-full h-12 bg-[#2B5329] text-white rounded-xl text-[14px] font-semibold active:bg-[#3D7338]">جایگزینی نسخه قبلی</button>
              <button onClick={() => setState('success')} className="w-full h-12 bg-[#FDFBF8] border border-[#DDD6CC] text-[#1A1A18] rounded-xl text-[14px] font-medium active:bg-[#EDE8E0]">نگهداری هر دو نسخه</button>
              <button onClick={() => setState('empty')} className="w-full h-12 text-[#6B6358] text-[14px]">لغو</button>
            </div>
          </div>
        )}

        {state === 'invalid' && (
          <div className="mt-2 bg-[#FDECEA] border border-[#F0B0B0] rounded-2xl p-5">
            <div className="flex items-center gap-3 mb-3">
              <div className="w-12 h-12 rounded-full bg-[#A84040]/20 flex items-center justify-center">
                <IcWarning size={22} className="text-[#A84040]" />
              </div>
              <p className="text-[15px] font-bold text-[#A84040]">فایل نامعتبر</p>
            </div>
            <p className="text-[13px] text-[#6B6358] leading-relaxed">
              فایل انتخاب‌شده قابل خواندن نیست یا آسیب دیده است. لطفاً فایل دیگری انتخاب کنید.
            </p>
            <button onClick={() => setState('empty')} className="mt-4 w-full h-12 bg-[#FDFBF8] border border-[#DDD6CC] text-[#1A1A18] rounded-xl text-[14px] font-medium">انتخاب مجدد</button>
          </div>
        )}

        {state === 'success' && (
          <div className="flex flex-col items-center justify-center min-h-60 gap-4">
            <div className="w-16 h-16 rounded-full bg-[#EBF2EB] flex items-center justify-center">
              <IcCheck size={32} className="text-[#2B5329]" />
            </div>
            <p className="text-[17px] font-bold text-[#2B5329]">کتاب با موفقیت وارد شد</p>
            <button onClick={() => navigate('library-grid')} className="h-12 px-8 bg-[#2B5329] text-white rounded-xl text-[15px] font-semibold active:bg-[#3D7338]">رفتن به کتابخانه</button>
          </div>
        )}
      </div>

      {state === 'selected' && (
        <div className="px-5 pb-2 shrink-0">
          <button
            onClick={() => setState('duplicate')}
            className="w-full h-12 bg-[#2B5329] text-[#F7F3ED] rounded-xl text-[15px] font-semibold active:bg-[#3D7338]"
          >
            افزودن به کتابخانه
          </button>
        </div>
      )}
      <NavBar />
    </div>
  )
}
