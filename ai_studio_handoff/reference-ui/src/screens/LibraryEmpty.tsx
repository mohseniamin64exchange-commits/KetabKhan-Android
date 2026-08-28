import StatusBar from '../components/StatusBar'
import NavBar from '../components/NavBar'
import { IcSearch, IcMore, IcAdd, IcImport } from '../components/Icons'
import type { NavProps } from '../types'

export default function LibraryEmpty({ navigate }: NavProps) {
  return (
    <div className="flex flex-col h-full bg-[#F7F3ED]" dir="rtl">
      <StatusBar />

      {/* Top App Bar */}
      <div className="flex items-center justify-between px-4 h-14 shrink-0">
        <h1 className="text-[20px] font-bold text-[#1A1A18]">کتابخانه من</h1>
        <div className="flex items-center gap-1">
          <button className="w-12 h-12 flex items-center justify-center rounded-full text-[#2B5329] active:bg-[#2B5329]/10">
            <IcSearch size={22} />
          </button>
          <button className="w-12 h-12 flex items-center justify-center rounded-full text-[#6B6358] active:bg-[#2B5329]/10">
            <IcMore size={22} />
          </button>
        </div>
      </div>

      {/* Empty State */}
      <div className="flex-1 flex flex-col items-center justify-center px-8 gap-6">
        <div className="w-28 h-28 rounded-3xl bg-[#EDE8E0] flex items-center justify-center">
          <svg width="56" height="56" viewBox="0 0 56 56" fill="none">
            <rect x="10" y="8" width="26" height="34" rx="3" fill="#C8C0B4" />
            <rect x="14" y="12" width="18" height="2.5" rx="1.25" fill="#EDE8E0" />
            <rect x="14" y="17" width="14" height="2" rx="1" fill="#EDE8E0" />
            <rect x="14" y="22" width="16" height="2" rx="1" fill="#EDE8E0" />
            <rect x="16" y="12" width="26" height="34" rx="3" fill="#B0A898" opacity="0.6" />
            <rect x="20" y="16" width="18" height="2.5" rx="1.25" fill="#EDE8E0" opacity="0.8" />
            <rect x="20" y="21" width="14" height="2" rx="1" fill="#EDE8E0" opacity="0.8" />
          </svg>
        </div>
        <div className="flex flex-col items-center gap-2 text-center">
          <p className="text-[17px] font-semibold text-[#1A1A18]">هنوز کتابی در کتابخانه شما نیست</p>
          <p className="text-[14px] text-[#6B6358] leading-relaxed">
            می‌توانید از یک فایل PDF کتاب جدید بسازید یا یک کتاب آماده را وارد کنید.
          </p>
        </div>
        <div className="flex flex-col gap-3 w-full">
          <button
            onClick={() => navigate('select-pdf')}
            className="h-12 rounded-xl bg-[#2B5329] text-[#F7F3ED] text-[15px] font-semibold flex items-center justify-center gap-2 active:bg-[#3D7338]"
          >
            <IcAdd size={20} />
            ساخت کتاب جدید
          </button>
          <button
            onClick={() => navigate('import-book')}
            className="h-12 rounded-xl border border-[#2B5329] text-[#2B5329] text-[15px] font-semibold flex items-center justify-center gap-2 active:bg-[#2B5329]/5"
          >
            <IcImport size={20} />
            واردکردن کتاب
          </button>
        </div>
      </div>

      <NavBar />
    </div>
  )
}
