import StatusBar from '../components/StatusBar'
import NavBar from '../components/NavBar'
import { IcChevronLeft, IcCheck, IcWarning, IcX, IcLock } from '../components/Icons'
import type { NavProps } from '../types'

function StateCard({ title, children }: { title: string; children: React.ReactNode }) {
  return (
    <div className="mb-4">
      <p className="text-[12px] font-bold text-[#6B6358] uppercase mb-2 tracking-wide">{title}</p>
      {children}
    </div>
  )
}

export default function States({ navigate, goBack }: NavProps) {
  return (
    <div className="flex flex-col h-full bg-[#F7F3ED]" dir="rtl">
      <StatusBar />

      <div className="flex items-center px-2 h-14 shrink-0">
        <button onClick={goBack} className="w-12 h-12 flex items-center justify-center rounded-full text-[#1A1A18]">
          <IcChevronLeft size={24} />
        </button>
        <h1 className="text-[18px] font-bold text-[#1A1A18] flex-1 text-center pr-10">حالت‌های سیستم</h1>
      </div>

      <div className="flex-1 overflow-y-auto px-4 pb-4">

        {/* Loading */}
        <StateCard title="بارگذاری">
          <div className="bg-[#FDFBF8] rounded-2xl border border-[#DDD6CC] p-6 flex flex-col items-center gap-3">
            <div className="w-12 h-12 rounded-full border-2 border-[#EDE8E0] border-t-[#2B5329] animate-spin" />
            <p className="text-[14px] text-[#6B6358]">در حال بارگذاری...</p>
          </div>
        </StateCard>

        {/* No internet */}
        <StateCard title="بدون اینترنت">
          <div className="bg-[#FDF3E7] border border-[#F0D8B0] rounded-2xl p-4 flex items-center gap-3">
            <IcWarning size={22} className="text-[#B87A28]" />
            <div>
              <p className="text-[14px] font-semibold text-[#B87A28]">اتصال به اینترنت برقرار نیست</p>
              <p className="text-[12px] text-[#6B6358]">پردازش پایه بدون اینترنت انجام می‌شود.</p>
            </div>
          </div>
        </StateCard>

        {/* Storage full */}
        <StateCard title="فضای ذخیره پر است">
          <div className="bg-[#FDECEA] border border-[#F0B0B0] rounded-2xl p-4 flex items-start gap-3">
            <IcWarning size={22} className="text-[#A84040] shrink-0 mt-0.5" />
            <div>
              <p className="text-[14px] font-semibold text-[#A84040]">فضای ذخیره‌سازی پر است</p>
              <p className="text-[12px] text-[#6B6358] mt-0.5">برای ادامه، فضای آزاد کنید.</p>
              <button className="mt-2 text-[12px] font-semibold text-[#A84040] border border-[#A84040]/30 rounded-lg px-3 py-1.5">مدیریت فضا</button>
            </div>
          </div>
        </StateCard>

        {/* Damaged PDF */}
        <StateCard title="PDF آسیب‌دیده">
          <div className="bg-[#FDECEA] border border-[#F0B0B0] rounded-2xl p-4">
            <p className="text-[14px] font-semibold text-[#A84040] mb-1">فایل PDF آسیب دیده است</p>
            <p className="text-[12px] text-[#6B6358]">این فایل قابل پردازش نیست. لطفاً فایل دیگری انتخاب کنید.</p>
          </div>
        </StateCard>

        {/* Password protected PDF */}
        <StateCard title="PDF رمزدار">
          <div className="bg-[#FDFBF8] border border-[#DDD6CC] rounded-2xl p-4">
            <p className="text-[14px] font-semibold text-[#1A1A18] mb-1">فایل رمزگذاری‌شده است</p>
            <p className="text-[12px] text-[#6B6358] mb-3">برای دسترسی، رمز عبور فایل PDF را وارد کنید.</p>
            <div className="flex items-center gap-2 bg-[#F7F3ED] border border-[#DDD6CC] rounded-xl px-3 h-12 mb-2">
              <input
                type="password"
                placeholder="رمز عبور"
                className="flex-1 text-[14px] text-[#1A1A18] bg-transparent outline-none"
                style={{ fontFamily: 'Vazirmatn, Tahoma, sans-serif', direction: 'rtl' }}
              />
            </div>
            <div className="flex items-center gap-2 text-[#6B6358]">
              <IcLock size={16} />
              <p className="text-[12px]">رمز عبور ذخیره نمی‌شود.</p>
            </div>
          </div>
        </StateCard>

        {/* Import success snackbar */}
        <StateCard title="اسنک‌بار / اعلان">
          <div className="flex flex-col gap-2">
            <div className="bg-[#1E2020] rounded-xl px-4 py-3 flex items-center gap-3">
              <IcCheck size={18} className="text-[#3A7A3A] shrink-0" />
              <p className="text-[13px] text-[#D8D4CC] flex-1">کتاب با موفقیت به کتابخانه اضافه شد</p>
              <button className="text-[12px] font-semibold text-[#3A7A3A]">مشاهده</button>
            </div>
            <div className="bg-[#1E2020] rounded-xl px-4 py-3 flex items-center gap-3">
              <IcWarning size={18} className="text-[#B87A28] shrink-0" />
              <p className="text-[13px] text-[#D8D4CC] flex-1">اتصال اینترنت برقرار نیست</p>
            </div>
            <div className="bg-[#1E2020] rounded-xl px-4 py-3 flex items-center gap-3">
              <IcX size={18} className="text-[#A84040] shrink-0" />
              <p className="text-[13px] text-[#D8D4CC] flex-1">پردازش با خطا مواجه شد</p>
              <button className="text-[12px] font-semibold text-[#C8823A]">تلاش مجدد</button>
            </div>
          </div>
        </StateCard>

        {/* Online consent */}
        <StateCard title="رضایت پردازش آنلاین">
          <div className="bg-[#FDFBF8] border border-[#DDD6CC] rounded-2xl p-4">
            <p className="text-[14px] font-semibold text-[#1A1A18] mb-2">استفاده از پردازش آنلاین؟</p>
            <p className="text-[12px] text-[#6B6358] leading-relaxed mb-4">
              بخش‌های دشوار فایل PDF شما برای اصلاح بهتر پردازش می‌شوند. محتوای کامل کتاب ارسال نمی‌شود.
            </p>
            <div className="flex gap-2">
              <button className="flex-1 h-12 bg-[#2B5329] text-white rounded-xl text-[13px] font-semibold">موافقم</button>
              <button className="flex-1 h-12 bg-[#FDFBF8] border border-[#DDD6CC] text-[#6B6358] rounded-xl text-[13px] font-medium">خیر، ادامه آفلاین</button>
            </div>
          </div>
        </StateCard>

        {/* General confirm dialog */}
        <StateCard title="دیالوگ تأیید عمومی">
          <div className="bg-[#FDFBF8] border border-[#DDD6CC] rounded-2xl p-5 text-center">
            <p className="text-[16px] font-bold text-[#1A1A18] mb-2">آیا مطمئنید؟</p>
            <p className="text-[13px] text-[#6B6358] mb-4">این عملیات قابل بازگشت نیست.</p>
            <div className="flex gap-2">
              <button className="flex-1 h-12 bg-[#A84040] text-white rounded-xl text-[14px] font-semibold">تأیید</button>
              <button className="flex-1 h-12 bg-[#FDFBF8] border border-[#DDD6CC] text-[#1A1A18] rounded-xl text-[14px] font-medium">لغو</button>
            </div>
          </div>
        </StateCard>

      </div>
      <NavBar />
    </div>
  )
}
