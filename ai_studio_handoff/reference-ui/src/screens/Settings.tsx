import StatusBar from '../components/StatusBar'
import NavBar from '../components/NavBar'
import { IcChevronLeft, IcChevronRight, IcFolder } from '../components/Icons'
import type { NavProps } from '../types'

function Row({ label, value, onTap, arrow = true }: { label: string; value?: string; onTap?: () => void; arrow?: boolean }) {
  return (
    <button
      onClick={onTap}
      className="flex items-center justify-between px-5 py-4 border-b border-[#EDE8E0] last:border-b-0 w-full active:bg-[#EDE8E0] text-right"
    >
      <span className="text-[14px] text-[#1A1A18]">{label}</span>
      <div className="flex items-center gap-1">
        {value && <span className="text-[13px] text-[#6B6358]">{value}</span>}
        {arrow && <IcChevronRight size={18} className="text-[#B0A898]" />}
      </div>
    </button>
  )
}

function Toggle({ label, note }: { label: string; note?: string }) {
  return (
    <div className="flex items-center justify-between px-5 py-4 border-b border-[#EDE8E0] last:border-b-0">
      <div>
        <p className="text-[14px] text-[#1A1A18]">{label}</p>
        {note && <p className="text-[12px] text-[#6B6358] mt-0.5">{note}</p>}
      </div>
      <div className="w-12 h-7 rounded-full bg-[#DDD6CC] relative">
        <div className="w-5 h-5 rounded-full bg-white shadow-sm absolute top-1 left-1" />
      </div>
    </div>
  )
}

function Section({ title, children }: { title: string; children: React.ReactNode }) {
  return (
    <div className="mb-4">
      <p className="text-[12px] font-bold text-[#6B6358] uppercase px-5 mb-2 tracking-wide">{title}</p>
      <div className="bg-[#FDFBF8] rounded-2xl border border-[#DDD6CC] overflow-hidden">
        {children}
      </div>
    </div>
  )
}

export default function Settings({ navigate, goBack }: NavProps) {
  return (
    <div className="flex flex-col h-full bg-[#F7F3ED]" dir="rtl">
      <StatusBar />

      <div className="flex items-center px-2 h-14 shrink-0">
        <button onClick={goBack} className="w-12 h-12 flex items-center justify-center rounded-full text-[#1A1A18]">
          <IcChevronLeft size={24} />
        </button>
        <h1 className="text-[18px] font-bold text-[#1A1A18] flex-1 text-center pr-10">تنظیمات</h1>
      </div>

      <div className="flex-1 overflow-y-auto px-4 pb-4">
        <Section title="نمایش کتابخانه">
          <Row label="نمای پیش‌فرض" value="شبکه‌ای" />
          <Row label="مرتب‌سازی پیش‌فرض" value="آخرین مطالعه" />
        </Section>

        <Section title="تنظیمات پیش‌فرض مطالعه">
          <Row label="پوسته پیش‌فرض" value="روشن" onTap={() => navigate('reading-settings')} />
          <Row label="نوع پیمایش" value="پیمایش عمودی" />
          <Row label="قلم پیش‌فرض" value="وزیر" />
        </Section>

        <Section title="پردازش با اینترنت">
          <Toggle label="بهبود OCR با اینترنت" note="فقط بخش‌های دشوار پردازش می‌شوند" />
        </Section>

        <Section title="مدیریت فضای ذخیره‌سازی">
          <div className="px-5 py-4 border-b border-[#EDE8E0]">
            <div className="flex justify-between mb-2">
              <span className="text-[14px] text-[#1A1A18]">کتاب‌ها</span>
              <span className="text-[13px] text-[#6B6358]">۱۲.۴ مگابایت</span>
            </div>
            <div className="h-1.5 bg-[#EDE8E0] rounded-full overflow-hidden">
              <div className="h-full bg-[#2B5329] rounded-full" style={{ width: '35%' }} />
            </div>
          </div>
          <div className="px-5 py-3">
            <div className="flex items-start gap-2">
              <IcFolder size={20} className="text-[#2B5329] shrink-0 mt-0.5" />
              <p className="text-[12px] text-[#6B6358] leading-relaxed">
                فایل‌های PDF اصلی که از گوشی انتخاب شده‌اند <strong>هرگز به‌طور خودکار حذف نمی‌شوند.</strong>
              </p>
            </div>
            <p className="text-[12px] text-[#6B6358] mt-1.5 leading-relaxed">
              تنها یک نسخه موقت داخلی ممکن است پس از تبدیل موفق در نسخه‌های بعدی برنامه پاک شود.
            </p>
          </div>
          <Row label="پاک‌کردن فایل‌های موقت" value="۰ بایت" />
        </Section>

        <Section title="پشتیبان‌گیری و بازیابی">
          <Row label="پشتیبان‌گیری و بازیابی" onTap={() => navigate('backup')} />
        </Section>

        <Section title="درباره برنامه">
          <Row label="نسخه برنامه" value="۱.۰.۰" arrow={false} />
          <Row label="تغییرات نسخه" />
          <Row label="گزارش مشکل" />
        </Section>
      </div>
      <NavBar />
    </div>
  )
}
