import { useState } from 'react'
import StatusBar from '../components/StatusBar'
import { IcBook, IcInfo, IcShare, IcEdit, IcDelete, IcChevronLeft, IcFolder } from '../components/Icons'
import type { NavProps } from '../types'

const OPTIONS = [
  { icon: IcBook, label: 'بازکردن کتاب', screen: 'reader-controls' as const, destructive: false },
  { icon: IcInfo, label: 'اطلاعات کتاب', screen: null, destructive: false },
  { icon: IcShare, label: 'ساخت فایل کتاب', screen: 'export-book' as const, destructive: false },
  { icon: IcShare, label: 'اشتراک‌گذاری', screen: null, destructive: false },
  { icon: IcEdit, label: 'تغییر جلد و مشخصات', screen: 'book-details' as const, destructive: false },
  { icon: IcDelete, label: 'حذف از کتابخانه', screen: null, destructive: true },
]

export default function BookOptions({ navigate, goBack }: NavProps) {
  const [showDeleteConfirm, setShowDeleteConfirm] = useState(false)

  return (
    <div className="flex flex-col h-full bg-[#F7F3ED]" dir="rtl">
      <StatusBar />

      <div className="flex items-center px-2 h-14 shrink-0">
        <button onClick={goBack} className="w-12 h-12 flex items-center justify-center rounded-full text-[#1A1A18]">
          <IcChevronLeft size={24} />
        </button>
        <h1 className="text-[18px] font-bold text-[#1A1A18] flex-1 text-center pr-10">گزینه‌های کتاب</h1>
      </div>

      {/* Book info header */}
      <div className="flex items-center gap-4 px-5 pb-5">
        <div
          className="w-16 h-20 rounded-xl shadow-md shrink-0"
          style={{ background: 'linear-gradient(160deg, #3D5A47 0%, #6B8F71 100%)' }}
        />
        <div>
          <p className="text-[16px] font-bold text-[#1A1A18]">جزیره سرگردانی</p>
          <p className="text-[13px] text-[#6B6358]">سیمین دانشور</p>
          <p className="text-[12px] text-[#2B5329] mt-1">۴۲٪ خوانده شد</p>
        </div>
      </div>

      {/* Options list */}
      <div className="bg-[#FDFBF8] mx-4 rounded-2xl border border-[#DDD6CC] overflow-hidden">
        {OPTIONS.map((opt, i) => (
          <button
            key={i}
            onClick={() => {
              if (opt.destructive) {
                setShowDeleteConfirm(true)
              } else if (opt.screen) {
                navigate(opt.screen)
              }
            }}
            className={`flex items-center gap-4 w-full px-5 py-4 border-b border-[#EDE8E0] last:border-b-0 active:bg-[#EDE8E0] ${opt.destructive ? 'text-[#A84040]' : 'text-[#1A1A18]'}`}
          >
            <opt.icon size={22} className={opt.destructive ? 'text-[#A84040]' : 'text-[#6B6358]'} />
            <span className="text-[15px]">{opt.label}</span>
          </button>
        ))}
      </div>

      {/* Delete confirmation dialog */}
      {showDeleteConfirm && (
        <div className="absolute inset-0 z-50 flex items-end justify-center">
          <div className="absolute inset-0 bg-black/40" onClick={() => setShowDeleteConfirm(false)} />
          <div className="relative w-full bg-[#FDFBF8] rounded-t-2xl pb-6 px-5 pt-2" onClick={e => e.stopPropagation()}>
            <div className="w-10 h-1 bg-[#DDD6CC] rounded-full mx-auto mb-4" />
            <div className="w-12 h-12 rounded-full bg-[#FDECEA] flex items-center justify-center mx-auto mb-3">
              <IcDelete size={24} className="text-[#A84040]" />
            </div>
            <h3 className="text-[17px] font-bold text-[#1A1A18] text-center mb-2">حذف از کتابخانه؟</h3>
            <p className="text-[13px] text-[#6B6358] text-center leading-relaxed mb-1">
              نسخه این کتاب از کتابخانه برنامه حذف می‌شود.
            </p>
            <div className="flex items-start gap-3 text-[#6B6358] mb-5 bg-[#F7F3ED] rounded-xl px-4 py-3">
              <IcFolder size={20} className="text-[#2B5329] shrink-0 mt-0.5" />
              <p className="text-[12px] text-right leading-relaxed">
                فایل PDF اصلی شما که در حافظه گوشی ذخیره شده، <strong>حذف نخواهد شد</strong> و همچنان در دسترس خواهد بود.
              </p>
            </div>
            <button
              onClick={() => { setShowDeleteConfirm(false); navigate('library-grid') }}
              className="w-full h-12 bg-[#A84040] text-white rounded-xl text-[15px] font-semibold mb-2 active:opacity-90"
            >
              حذف از کتابخانه
            </button>
            <button
              onClick={() => setShowDeleteConfirm(false)}
              className="w-full h-12 text-[#1A1A18] text-[15px] font-medium"
            >
              لغو
            </button>
          </div>
        </div>
      )}
    </div>
  )
}
