import StatusBar from '../components/StatusBar'
import NavBar from '../components/NavBar'
import { IcChevronLeft, IcWarning } from '../components/Icons'
import type { NavProps } from '../types'

export default function FinalPreview({ navigate, goBack }: NavProps) {
  return (
    <div className="flex flex-col h-full bg-[#F7F3ED]" dir="rtl">
      <StatusBar />

      {/* Review bar */}
      <div className="flex items-center px-2 h-14 shrink-0 gap-1 border-b border-[#EDE8E0]">
        <button onClick={goBack} className="w-12 h-12 flex items-center justify-center rounded-full text-[#1A1A18]">
          <IcChevronLeft size={24} />
        </button>
        <div className="flex-1 flex items-center justify-between pr-2">
          <h1 className="text-[16px] font-bold text-[#1A1A18]">پیش‌نمایش نهایی</h1>
          <div className="flex items-center gap-1.5 bg-[#FDF3E7] border border-[#F0D8B0] rounded-full px-3 py-1">
            <IcWarning size={14} className="text-[#B87A28]" />
            <span className="text-[12px] font-semibold text-[#B87A28]">۱ مورد بررسی‌نشده</span>
          </div>
        </div>
      </div>

      {/* Reading preview */}
      <div className="flex-1 overflow-y-auto bg-[#FAF6F0] px-6 py-6">
        <h2 className="text-[19px] font-bold text-[#1A1A18] mb-4 leading-snug">
          فصل اول: آشنایی با موضوع
        </h2>
        <p className="text-[15px] text-[#1A1A18] leading-[2] text-justify mb-5">
          در آغاز هر داستانی، دنیایی پدید می‌آید که خواننده را به درون خود می‌کشد. این دنیا نه تنها از کلمات ساخته شده، بلکه از احساسی است که میان سطرها جاری است. نویسنده با هر جمله، تصویری می‌سازد و با هر فصل، راهی می‌گشاید.
        </p>

        {/* Image with caption */}
        <div className="mb-5">
          <div className="w-full h-32 rounded-xl bg-[#EDE8E0] flex items-center justify-center mb-2">
            <svg width="40" height="40" viewBox="0 0 40 40" fill="none">
              <rect x="4" y="4" width="32" height="32" rx="4" stroke="#B0A898" strokeWidth="1.5" />
              <circle cx="14" cy="15" r="4" stroke="#B0A898" strokeWidth="1.5" />
              <path d="M4 28l8-6 6 5 6-8 12 9" stroke="#B0A898" strokeWidth="1.5" strokeLinecap="round" />
            </svg>
          </div>
          <p className="text-[12px] text-[#6B6358] text-center">تصویر ۱-۱: نمونه‌ای از ساختار روایی در ادبیات داستانی</p>
        </div>

        <p className="text-[15px] text-[#1A1A18] leading-[2] text-justify mb-5">
          مطالعه این فصل خواننده را با مفاهیم پایه‌ای آشنا می‌کند. هر مفهوم با مثال‌های ملموس توضیح داده شده تا درک آن آسان‌تر باشد. در پایان این فصل، خواننده می‌تواند تفاوت‌های اصلی میان رویکردهای مختلف را شناسایی کند.
          <sup className="text-[12px] text-[#2B5329] cursor-pointer">¹</sup>
        </p>

        {/* Footnote */}
        <div className="border-t border-[#DDD6CC] pt-3 mt-4">
          <p className="text-[12px] text-[#6B6358] leading-relaxed">
            ¹ برای مطالعه بیشتر، به فصل سوم همین کتاب مراجعه کنید.
          </p>
        </div>
      </div>

      <div className="px-5 pb-2 flex flex-col gap-2 shrink-0 bg-[#F7F3ED] border-t border-[#EDE8E0] pt-3">
        <button
          onClick={() => navigate('library-grid')}
          className="w-full h-12 bg-[#2B5329] text-[#F7F3ED] rounded-xl text-[15px] font-semibold active:bg-[#3D7338]"
        >
          تأیید و افزودن به کتابخانه
        </button>
        <button
          onClick={() => navigate('structure-review')}
          className="w-full h-12 text-[#2B5329] text-[14px] font-medium active:opacity-70"
        >
          بازگشت و اصلاح
        </button>
      </div>
      <NavBar />
    </div>
  )
}
