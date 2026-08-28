import { useState } from 'react'
import StatusBar from '../components/StatusBar'
import NavBar from '../components/NavBar'
import { IcChevronLeft, IcCheck, IcX } from '../components/Icons'
import type { NavProps } from '../types'

const ISSUES = [
  {
    title: 'عنوان نامشخص',
    desc: 'این بخش ممکن است عنوان فصل یا ادامه متن قبلی باشد.',
    original: 'کاربردها\nاین فصل به بررسی کاربردهای عملی موضوع می‌پردازد...',
    converted: '۱ـ۲ کاربردها',
  },
  {
    title: 'اتصال پاورقی مشخص نیست',
    desc: 'پاورقی ۱۲ ممکن است به پاراگراف اشتباهی متصل شده باشد.',
    original: '...که در سال ۱۳۲۵ نوشته شد.¹\n\n¹ منبع: تاریخ ادبیات معاصر',
    converted: '...که در سال ۱۳۲۵ نوشته شد. [پاورقی ۱۲]',
  },
]

export default function IssueReview({ navigate, goBack }: NavProps) {
  const [issueIdx, setIssueIdx] = useState(0)
  const [tab, setTab] = useState<'original' | 'converted'>('original')
  const issue = ISSUES[issueIdx]

  return (
    <div className="flex flex-col h-full bg-[#F7F3ED]" dir="rtl">
      <StatusBar />

      <div className="flex items-center px-2 h-14 shrink-0 gap-1">
        <button onClick={goBack} className="w-12 h-12 flex items-center justify-center rounded-full text-[#1A1A18]">
          <IcChevronLeft size={24} />
        </button>
        <div className="flex-1 text-center pr-10">
          <p className="text-[14px] font-bold text-[#1A1A18]">بررسی مورد</p>
          <p className="text-[12px] text-[#6B6358]">{issueIdx + 1} از {ISSUES.length}</p>
        </div>
      </div>

      <div className="flex-1 overflow-y-auto px-4 pb-4">
        {/* Issue header */}
        <div className="bg-[#FDF3E7] border border-[#F0D8B0] rounded-2xl p-4 mb-4">
          <p className="text-[15px] font-bold text-[#B87A28]">{issue.title}</p>
          <p className="text-[13px] text-[#6B6358] mt-1 leading-relaxed">{issue.desc}</p>
        </div>

        {/* Tabs */}
        <div className="flex gap-1 bg-[#EDE8E0] rounded-xl p-1 mb-4">
          {(['original', 'converted'] as const).map(t => (
            <button
              key={t}
              onClick={() => setTab(t)}
              className="flex-1 h-12 rounded-lg text-[13px] font-medium transition-colors"
              style={{
                background: tab === t ? '#FDFBF8' : 'transparent',
                color: tab === t ? '#1A1A18' : '#6B6358',
              }}
            >
              {t === 'original' ? 'PDF اصلی' : 'نسخه تبدیل‌شده'}
            </button>
          ))}
        </div>

        {/* Content */}
        <div className="bg-[#FDFBF8] rounded-2xl border border-[#DDD6CC] p-4 min-h-40">
          <p
            className="text-[14px] text-[#1A1A18] leading-relaxed whitespace-pre-line"
            style={{ fontFamily: 'Vazirmatn, Tahoma, sans-serif', direction: 'rtl' }}
          >
            {tab === 'original' ? issue.original : issue.converted}
          </p>
        </div>
      </div>

      {/* Actions */}
      <div className="px-4 pb-2 flex flex-col gap-2 shrink-0">
        <div className="flex gap-2">
          <button
            onClick={() => issueIdx < ISSUES.length - 1 ? setIssueIdx(i => i + 1) : navigate('structure-review')}
            className="flex-1 h-12 bg-[#2B5329] text-[#F7F3ED] rounded-xl text-[14px] font-semibold flex items-center justify-center gap-2 active:bg-[#3D7338]"
          >
            <IcCheck size={18} />
            تأیید
          </button>
          <button className="flex-1 h-12 bg-[#FDFBF8] border border-[#DDD6CC] text-[#1A1A18] rounded-xl text-[14px] font-medium active:bg-[#EDE8E0]">
            ویرایش
          </button>
          <button
            onClick={() => issueIdx < ISSUES.length - 1 ? setIssueIdx(i => i + 1) : navigate('structure-review')}
            className="flex-1 h-12 bg-[#FDFBF8] border border-[#DDD6CC] text-[#6B6358] rounded-xl text-[14px] font-medium active:bg-[#EDE8E0]"
          >
            نادیده‌گرفتن
          </button>
        </div>

        {/* Nav */}
        <div className="flex justify-between items-center px-2">
          <button
            onClick={() => setIssueIdx(i => Math.max(0, i - 1))}
            className="text-[13px] text-[#2B5329] font-medium disabled:text-[#DDD6CC]"
            disabled={issueIdx === 0}
          >
            ← قبلی
          </button>
          <span className="text-[12px] text-[#6B6358]">{issueIdx + 1}/{ISSUES.length}</span>
          <button
            onClick={() => setIssueIdx(i => Math.min(ISSUES.length - 1, i + 1))}
            className="text-[13px] text-[#2B5329] font-medium disabled:text-[#DDD6CC]"
            disabled={issueIdx === ISSUES.length - 1}
          >
            بعدی →
          </button>
        </div>
      </div>
      <NavBar />
    </div>
  )
}
