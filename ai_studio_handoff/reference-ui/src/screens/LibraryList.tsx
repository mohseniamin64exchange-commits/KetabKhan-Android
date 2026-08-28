import StatusBar from '../components/StatusBar'
import NavBar from '../components/NavBar'
import { IcSearch, IcGrid, IcSort, IcMore } from '../components/Icons'
import type { NavProps, Book } from '../types'
import { SAMPLE_BOOKS } from '../types'

const STATUS_MAP: Record<Book['status'], { label: string; color: string }> = {
  ready: { label: 'آماده مطالعه', color: '#3A7A3A' },
  reading: { label: 'در حال مطالعه', color: '#2B5329' },
  processing: { label: 'در حال پردازش', color: '#B87A28' },
  review: { label: 'نیاز به بررسی', color: '#B87A28' },
  failed: { label: 'خطا در تبدیل', color: '#A84040' },
  imported: { label: 'وارد شده', color: '#2B5329' },
}

function BookRow({ book, onTap }: { book: Book; onTap: () => void }) {
  const st = STATUS_MAP[book.status]
  return (
    <button
      onClick={onTap}
      className="flex gap-3 px-4 py-3 items-center active:bg-[#EDE8E0] w-full text-right"
    >
      <div
        className="w-12 h-16 rounded-lg shrink-0 shadow-sm"
        style={{ background: `linear-gradient(160deg, ${book.coverColor} 0%, ${book.coverAccent} 100%)` }}
      />
      <div className="flex-1 min-w-0">
        <p className="text-[15px] font-semibold text-[#1A1A18] line-clamp-1">{book.title}</p>
        <p className="text-[13px] text-[#6B6358] mt-0.5">{book.author}</p>
        <p className="text-[12px] font-medium mt-1" style={{ color: st.color }}>{st.label}</p>
        {book.lastRead && (
          <p className="text-[12px] text-[#6B6358] mt-0.5">{book.lastRead}</p>
        )}
        {book.status === 'reading' && (
          <div className="mt-1.5">
            <div className="h-1 bg-[#EDE8E0] rounded-full overflow-hidden">
              <div className="h-full bg-[#2B5329] rounded-full" style={{ width: `${book.progress}%` }} />
            </div>
          </div>
        )}
      </div>
      <button className="w-12 h-12 flex items-center justify-center text-[#6B6358] shrink-0">
        <IcMore size={18} />
      </button>
    </button>
  )
}

export default function LibraryList({ navigate, goBack }: NavProps) {
  return (
    <div className="flex flex-col h-full bg-[#F7F3ED]" dir="rtl">
      <StatusBar />

      <div className="flex items-center justify-between px-4 h-14 shrink-0">
        <h1 className="text-[20px] font-bold text-[#1A1A18]">کتابخانه من</h1>
        <div className="flex items-center gap-1">
          <button className="w-12 h-12 flex items-center justify-center rounded-full text-[#2B5329]">
            <IcSearch size={22} />
          </button>
          <button onClick={() => navigate('library-grid')} className="w-12 h-12 flex items-center justify-center rounded-full text-[#6B6358]">
            <IcGrid size={22} />
          </button>
          <button className="w-12 h-12 flex items-center justify-center rounded-full text-[#6B6358]">
            <IcSort size={22} />
          </button>
          <button className="w-12 h-12 flex items-center justify-center rounded-full text-[#6B6358]">
            <IcMore size={22} />
          </button>
        </div>
      </div>

      <div className="flex-1 overflow-y-auto divide-y divide-[#EDE8E0]">
        {SAMPLE_BOOKS.map(book => (
          <BookRow
            key={book.id}
            book={book}
            onTap={() =>
              book.status === 'review'
                ? navigate('structure-review')
                : book.status === 'processing'
                ? navigate('conversion')
                : navigate('reader-controls')
            }
          />
        ))}
      </div>

      <NavBar />
    </div>
  )
}
