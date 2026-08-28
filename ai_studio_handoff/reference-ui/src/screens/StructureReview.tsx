import StatusBar from '../components/StatusBar'
import NavBar from '../components/NavBar'
import { IcChevronLeft, IcWarning, IcCheck } from '../components/Icons'
import type { NavProps } from '../types'

const CHAPTERS = [
  { title: 'پیشگفتار', level: 0, confident: true },
  { title: 'مقدمه', level: 0, confident: true },
  { title: 'فصل اول: آشنایی با موضوع', level: 0, confident: true },
  { title: '۱ـ۱ مفاهیم اصلی', level: 1, confident: true },
  { title: '۱ـ۲ کاربردها', level: 1, confident: false },
  { title: 'فصل دوم: ادامه بحث', level: 0, confident: true },
  { title: '۲ـ۱ تحلیل نتایج', level: 1, confident: true },
  { title: '۲ـ۲ بحث و نتیجه‌گیری', level: 1, confident: false },
  { title: 'فصل سوم: جمع‌بندی', level: 0, confident: true },
  { title: 'منابع', level: 0, confident: true },
  { title: 'پیوست‌ها', level: 0, confident: true },
]

export default function StructureReview({ navigate, goBack }: NavProps) {
  const uncertainCount = CHAPTERS.filter(c => !c.confident).length

  return (
    <div className="flex flex-col h-full bg-[#F7F3ED]" dir="rtl">
      <StatusBar />

      <div className="flex items-center px-2 h-14 shrink-0 gap-1">
        <button onClick={goBack} className="w-12 h-12 flex items-center justify-center rounded-full text-[#1A1A18]">
          <IcChevronLeft size={24} />
        </button>
        <h1 className="text-[18px] font-bold text-[#1A1A18] flex-1 text-center pr-10">بررسی ساختار کتاب</h1>
      </div>

      <div className="flex-1 overflow-y-auto px-4 pb-4">
        {/* Summary */}
        <div className="flex gap-3 mb-4">
          <div className="flex-1 bg-[#FDFBF8] rounded-xl border border-[#EDE8E0] p-3 text-center">
            <p className="text-[22px] font-bold text-[#2B5329]">{CHAPTERS.length}</p>
            <p className="text-[12px] text-[#6B6358]">بخش شناسایی‌شده</p>
          </div>
          <div className="flex-1 bg-[#FDF3E7] rounded-xl border border-[#F0D8B0] p-3 text-center">
            <p className="text-[22px] font-bold text-[#B87A28]">{uncertainCount}</p>
            <p className="text-[12px] text-[#6B6358]">نیاز به بررسی</p>
          </div>
        </div>

        {/* Legend */}
        <div className="flex gap-4 mb-4">
          <div className="flex items-center gap-1.5">
            <div className="w-3 h-3 rounded-full bg-[#2B5329]" />
            <span className="text-[12px] text-[#6B6358]">مطمئن</span>
          </div>
          <div className="flex items-center gap-1.5">
            <div className="w-3 h-3 rounded-full bg-[#B87A28]" />
            <span className="text-[12px] text-[#6B6358]">نیازمند بررسی</span>
          </div>
        </div>

        {/* Chapter Tree */}
        <div className="bg-[#FDFBF8] rounded-2xl border border-[#DDD6CC] overflow-hidden">
          {CHAPTERS.map((ch, i) => (
            <div
              key={i}
              className="flex items-center gap-3 px-4 py-3 border-b border-[#EDE8E0] last:border-b-0 active:bg-[#EDE8E0]"
              style={{ paddingRight: ch.level === 1 ? '2.5rem' : '1rem' }}
            >
              <div
                className="w-2 h-2 rounded-full shrink-0"
                style={{ background: ch.confident ? '#2B5329' : '#B87A28' }}
              />
              <p
                className="flex-1 text-[14px]"
                style={{
                  color: ch.confident ? '#1A1A18' : '#1A1A18',
                  fontWeight: ch.level === 0 ? 600 : 400,
                }}
              >
                {ch.title}
              </p>
              {!ch.confident && (
                <IcWarning size={16} className="text-[#B87A28] shrink-0" />
              )}
              <button className="w-12 h-12 flex items-center justify-center text-[#6B6358] shrink-0">
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round">
                  <circle cx="12" cy="5" r="1" fill="currentColor" />
                  <circle cx="12" cy="12" r="1" fill="currentColor" />
                  <circle cx="12" cy="19" r="1" fill="currentColor" />
                </svg>
              </button>
            </div>
          ))}
        </div>
      </div>

      <div className="px-4 pb-2 flex flex-col gap-2 shrink-0">
        <button
          onClick={() => navigate('final-preview')}
          className="w-full h-12 bg-[#2B5329] text-[#F7F3ED] rounded-xl text-[15px] font-semibold active:bg-[#3D7338]"
        >
          پیش‌نمایش کتاب
        </button>
        <button
          onClick={() => navigate('issue-review')}
          className="w-full h-12 bg-[#FDFBF8] border border-[#DDD6CC] text-[#1A1A18] rounded-xl text-[15px] font-medium active:bg-[#EDE8E0]"
        >
          اصلاح موارد مشکوک ({uncertainCount})
        </button>
      </div>
      <NavBar />
    </div>
  )
}
