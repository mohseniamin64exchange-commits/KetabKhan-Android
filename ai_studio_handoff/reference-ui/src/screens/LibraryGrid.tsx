import { useState } from 'react'
import StatusBar from '../components/StatusBar'
import NavBar from '../components/NavBar'
import { IcSearch, IcList, IcSort, IcMore, IcAdd } from '../components/Icons'
import type { NavProps, Book } from '../types'
import { SAMPLE_BOOKS } from '../types'

function BookCover({ book, onClick }: { book: Book; onClick: () => void }) {
  const statusLabel: Record<Book['status'], string | null> = {
    reading: null,
    ready: null,
    processing: 'در حال پردازش',
    review: 'نیاز به بررسی',
    failed: 'خطا در تبدیل',
    imported: 'وارد شده',
  }
  const statusColor: Record<Book['status'], string> = {
    reading: '#2B5329',
    ready: '#3A7A3A',
    processing: '#B87A28',
    review: '#B87A28',
    failed: '#A84040',
    imported: '#2B5329',
  }

  return (
    <button
      onClick={onClick}
      className="flex flex-col gap-2 active:opacity-80 transition-opacity text-right"
    >
      {/* Cover */}
      <div
        className="w-full aspect-[2/3] rounded-xl relative overflow-hidden shadow-md"
        style={{ background: `linear-gradient(160deg, ${book.coverColor} 0%, ${book.coverAccent} 100%)` }}
      >
        <div className="absolute inset-0 flex flex-col justify-end p-3">
          <p className="text-white text-[12px] font-bold leading-snug line-clamp-2">{book.title}</p>
          <p className="text-white/70 text-[12px] mt-0.5">{book.author}</p>
        </div>
        {/* Processing overlay */}
        {book.status === 'processing' && (
          <div className="absolute inset-0 bg-black/40 flex items-center justify-center">
            <div className="flex flex-col items-center gap-2">
              <div className="w-12 h-12 rounded-full border-2 border-white/30 border-t-white animate-spin" />
              <p className="text-white text-[12px]">پردازش...</p>
            </div>
          </div>
        )}
        {/* Review badge */}
        {book.status === 'review' && (
          <div className="absolute top-2 right-2 bg-[#B87A28] rounded-md px-1.5 py-0.5">
            <p className="text-white text-[12px] font-semibold">بررسی</p>
          </div>
        )}
        {/* Progress bar */}
        {book.status === 'reading' && book.progress > 0 && (
          <div className="absolute bottom-0 inset-x-0 h-1 bg-white/20">
            <div className="h-full bg-white/70 transition-all" style={{ width: `${book.progress}%` }} />
          </div>
        )}
      </div>

      {/* Info */}
      <div className="flex flex-col gap-0.5">
        <p className="text-[13px] font-semibold text-[#1A1A18] line-clamp-1">{book.title}</p>
        <p className="text-[12px] text-[#6B6358] line-clamp-1">{book.author}</p>
        {statusLabel[book.status] ? (
          <p className="text-[12px] font-medium" style={{ color: statusColor[book.status] }}>
            {statusLabel[book.status]}
          </p>
        ) : book.status === 'reading' ? (
          <p className="text-[12px] text-[#6B6358]">{book.progress}٪ خوانده شد</p>
        ) : null}
      </div>
    </button>
  )
}

export default function LibraryGrid({ navigate }: NavProps) {
  const [showSort, setShowSort] = useState(false)
  const reading = SAMPLE_BOOKS.find(b => b.status === 'reading')

  return (
    <div className="flex flex-col h-full bg-[#F7F3ED]" dir="rtl">
      <StatusBar />

      {/* Top App Bar */}
      <div className="flex items-center justify-between px-4 h-14 shrink-0">
        <h1 className="text-[20px] font-bold text-[#1A1A18]">کتابخانه من</h1>
        <div className="flex items-center gap-1">
          <button className="w-12 h-12 flex items-center justify-center rounded-full text-[#2B5329]">
            <IcSearch size={22} />
          </button>
          <button onClick={() => navigate('library-list')} className="w-12 h-12 flex items-center justify-center rounded-full text-[#6B6358]">
            <IcList size={22} />
          </button>
          <button onClick={() => setShowSort(true)} className="w-12 h-12 flex items-center justify-center rounded-full text-[#6B6358]">
            <IcSort size={22} />
          </button>
          <button className="w-12 h-12 flex items-center justify-center rounded-full text-[#6B6358]">
            <IcMore size={22} />
          </button>
        </div>
      </div>

      <div className="flex-1 overflow-y-auto px-4 pb-4">
        {/* Continue Reading */}
        {reading && (
          <div className="mb-5">
            <p className="text-[13px] font-semibold text-[#6B6358] mb-3">ادامه مطالعه</p>
            <button
              onClick={() => navigate('reader-controls')}
              className="w-full bg-[#FDFBF8] rounded-2xl p-4 flex gap-4 items-center border border-[#DDD6CC] active:bg-[#EDE8E0] transition-colors shadow-sm"
            >
              <div
                className="w-14 h-20 rounded-lg shrink-0 shadow-sm"
                style={{ background: `linear-gradient(160deg, ${reading.coverColor} 0%, ${reading.coverAccent} 100%)` }}
              />
              <div className="flex flex-col gap-1 flex-1 text-right min-w-0">
                <p className="text-[15px] font-semibold text-[#1A1A18] line-clamp-1">{reading.title}</p>
                <p className="text-[13px] text-[#6B6358]">{reading.author}</p>
                <div className="mt-2">
                  <div className="flex items-center justify-between mb-1">
                    <span className="text-[12px] text-[#6B6358]">{reading.progress}٪</span>
                  </div>
                  <div className="h-1.5 bg-[#EDE8E0] rounded-full overflow-hidden">
                    <div className="h-full bg-[#2B5329] rounded-full" style={{ width: `${reading.progress}%` }} />
                  </div>
                </div>
              </div>
            </button>
          </div>
        )}

        {/* All Books */}
        <p className="text-[13px] font-semibold text-[#6B6358] mb-3">همه کتاب‌ها</p>
        <div className="grid grid-cols-2 gap-4">
          {SAMPLE_BOOKS.map(book => (
            <BookCover
              key={book.id}
              book={book}
              onClick={() =>
                book.status === 'review'
                  ? navigate('structure-review')
                  : book.status === 'processing'
                  ? navigate('conversion')
                  : navigate('reader-controls')
              }
            />
          ))}
        </div>
      </div>

      {/* FAB */}
      <div className="absolute bottom-12 left-4">
        <button
          onClick={() => navigate('select-pdf')}
          className="flex items-center gap-2 bg-[#2B5329] text-[#F7F3ED] rounded-2xl px-5 h-14 shadow-lg text-[14px] font-semibold active:bg-[#3D7338]"
        >
          <IcAdd size={22} />
          ساخت کتاب
        </button>
      </div>

      <NavBar />

      {/* Sort Bottom Sheet */}
      {showSort && (
        <div className="absolute inset-0 z-50" onClick={() => setShowSort(false)}>
          <div className="absolute inset-0 bg-black/30" />
          <div
            className="absolute bottom-0 inset-x-0 bg-[#FDFBF8] rounded-t-2xl pb-8"
            onClick={e => e.stopPropagation()}
          >
            <div className="w-10 h-1 bg-[#DDD6CC] rounded-full mx-auto mt-3 mb-4" />
            <p className="text-[16px] font-bold text-[#1A1A18] px-5 mb-3">مرتب‌سازی بر اساس</p>
            {['آخرین مطالعه', 'تاریخ افزودن', 'عنوان کتاب', 'نام نویسنده'].map((label, i) => (
              <button
                key={i}
                onClick={() => setShowSort(false)}
                className="flex items-center justify-between w-full px-5 py-3.5 text-[15px] text-[#1A1A18] active:bg-[#EDE8E0]"
              >
                <span>{label}</span>
                {i === 0 && (
                  <span className="w-5 h-5 rounded-full bg-[#2B5329] flex items-center justify-center">
                    <svg width="10" height="8" viewBox="0 0 10 8" fill="none">
                      <path d="M1 4L3.5 6.5L9 1" stroke="white" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round" />
                    </svg>
                  </span>
                )}
              </button>
            ))}
          </div>
        </div>
      )}
    </div>
  )
}
