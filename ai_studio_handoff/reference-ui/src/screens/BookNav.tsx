import { useState } from 'react'
import StatusBar from '../components/StatusBar'
import { IcChevronLeft, IcX, IcDelete, IcSearch } from '../components/Icons'
import type { NavProps } from '../types'

const CHAPTERS = [
  'پیشگفتار',
  'مقدمه',
  'فصل اول: آشنایی با موضوع',
  '    ۱ـ۱ مفاهیم اصلی',
  '    ۱ـ۲ کاربردها',
  'فصل دوم: ادامه بحث',
  '    ۲ـ۱ تحلیل نتایج',
  '    ۲ـ۲ بحث و نتیجه‌گیری',
  'فصل سوم: جمع‌بندی',
  'منابع',
  'پیوست‌ها',
]

const BOOKMARKS = [
  { chapter: 'فصل اول: آشنایی با موضوع', excerpt: 'داستان از آنجا آغاز می‌شود که مهری با چمدانی...', date: '۱۴۰۳/۰۵/۱۵' },
  { chapter: 'فصل دوم: ادامه بحث', excerpt: 'روزها می‌گذرند و مهری هر روز کمی بیشتر...', date: '۱۴۰۳/۰۵/۱۶' },
]

const SEARCH_RESULTS = [
  { chapter: 'فصل اول', excerpt: '...که مهری با چمدانی کوچک و دلی پر از تردید به شهر...', term: 'مهری' },
  { chapter: 'فصل دوم', excerpt: '...روزها می‌گذرند و مهری هر روز کمی بیشتر...', term: 'مهری' },
]

export default function BookNav({ navigate, goBack }: NavProps) {
  const [tab, setTab] = useState<'toc' | 'bookmarks' | 'search'>('toc')

  return (
    <div className="flex flex-col h-full bg-[#F7F3ED]" dir="rtl">
      <StatusBar />

      <div className="flex items-center px-2 h-14 shrink-0 gap-1 border-b border-[#EDE8E0]">
        <button onClick={goBack} className="w-12 h-12 flex items-center justify-center rounded-full text-[#1A1A18]">
          <IcChevronLeft size={24} />
        </button>
        <h1 className="text-[18px] font-bold text-[#1A1A18] flex-1 text-center pr-10">جزیره سرگردانی</h1>
      </div>

      {/* Tabs */}
      <div className="flex border-b border-[#EDE8E0] shrink-0">
        {([['toc', 'فهرست'], ['bookmarks', 'نشانک‌ها'], ['search', 'جست‌وجو']] as const).map(([key, label]) => (
          <button
            key={key}
            onClick={() => setTab(key)}
            className="flex-1 h-12 text-[13px] font-medium relative"
            style={{ color: tab === key ? '#2B5329' : '#6B6358' }}
          >
            {label}
            {tab === key && (
              <div className="absolute bottom-0 inset-x-0 h-0.5 bg-[#2B5329]" />
            )}
          </button>
        ))}
      </div>

      <div className="flex-1 overflow-y-auto">
        {tab === 'toc' && (
          <div className="py-2">
            {CHAPTERS.map((ch, i) => {
              const isSubChapter = ch.startsWith('    ')
              const isActive = i === 2
              return (
                <button
                  key={i}
                  onClick={() => navigate('reader-controls')}
                  className="flex items-center w-full px-5 py-3 active:bg-[#EDE8E0]"
                  style={{ paddingRight: isSubChapter ? '2.5rem' : '1.25rem' }}
                >
                  <p
                    className="text-right text-[14px]"
                    style={{
                      color: isActive ? '#2B5329' : '#1A1A18',
                      fontWeight: isActive ? 700 : isSubChapter ? 400 : 500,
                    }}
                  >
                    {ch.trim()}
                  </p>
                  {isActive && (
                    <div className="mr-auto w-1.5 h-1.5 rounded-full bg-[#2B5329]" />
                  )}
                </button>
              )
            })}
          </div>
        )}

        {tab === 'bookmarks' && (
          <div className="py-2">
            {BOOKMARKS.map((bm, i) => (
              <div key={i} className="flex items-start gap-3 px-5 py-4 border-b border-[#EDE8E0]">
                <div className="flex-1">
                  <p className="text-[13px] font-semibold text-[#2B5329]">{bm.chapter}</p>
                  <p className="text-[13px] text-[#6B6358] mt-1 leading-relaxed line-clamp-2">{bm.excerpt}</p>
                  <p className="text-[12px] text-[#B0A898] mt-1">{bm.date}</p>
                </div>
                <button className="w-12 h-12 flex items-center justify-center text-[#A84040] shrink-0 mt-0.5">
                  <IcDelete size={18} />
                </button>
              </div>
            ))}
          </div>
        )}

        {tab === 'search' && (
          <div>
            <div className="px-4 py-3 border-b border-[#EDE8E0]">
              <div className="flex items-center gap-2 bg-[#FDFBF8] border border-[#DDD6CC] rounded-xl px-3 h-12">
                <IcSearch size={18} className="text-[#6B6358]" />
                <input
                  defaultValue="مهری"
                  className="flex-1 text-[14px] text-[#1A1A18] bg-transparent outline-none"
                  style={{ fontFamily: 'Vazirmatn, Tahoma, sans-serif', direction: 'rtl' }}
                  placeholder="جست‌وجو در کتاب..."
                />
              </div>
            </div>
            <p className="text-[12px] text-[#6B6358] px-5 py-2">۲ نتیجه برای «مهری»</p>
            {SEARCH_RESULTS.map((r, i) => (
              <button
                key={i}
                onClick={() => navigate('reader-controls')}
                className="flex flex-col gap-1 w-full px-5 py-3.5 border-b border-[#EDE8E0] active:bg-[#EDE8E0] text-right"
              >
                <p className="text-[12px] font-semibold text-[#2B5329]">{r.chapter}</p>
                <p className="text-[13px] text-[#6B6358] leading-relaxed">
                  {r.excerpt.replace(r.term, `​`).split('​').map((part, j, arr) =>
                    j < arr.length - 1 ? [part, <span key={j} className="bg-[#EBF2EB] text-[#2B5329] rounded px-0.5">{r.term}</span>] : [part]
                  )}
                </p>
              </button>
            ))}
          </div>
        )}
      </div>
    </div>
  )
}
