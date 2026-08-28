import StatusBar from '../components/StatusBar'
import NavBar from '../components/NavBar'
import { IcChevronLeft } from '../components/Icons'
import type { NavProps } from '../types'

const Field = ({ label, value, required }: { label: string; value: string; required?: boolean }) => (
  <div className="flex flex-col gap-1">
    <label className="text-[12px] font-semibold text-[#6B6358]">
      {label}{required && <span className="text-[#A84040] mr-0.5">*</span>}
    </label>
    <input
      defaultValue={value}
      className="h-12 rounded-xl border border-[#DDD6CC] bg-[#FDFBF8] px-3 text-[14px] text-[#1A1A18] outline-none focus:border-[#2B5329]"
      style={{ fontFamily: 'Vazirmatn, Tahoma, sans-serif', direction: 'rtl' }}
    />
  </div>
)

export default function BookDetails({ navigate, goBack }: NavProps) {
  return (
    <div className="flex flex-col h-full bg-[#F7F3ED]" dir="rtl">
      <StatusBar />

      <div className="flex items-center px-2 h-14 shrink-0 gap-1">
        <button onClick={goBack} className="w-12 h-12 flex items-center justify-center rounded-full text-[#1A1A18]">
          <IcChevronLeft size={24} />
        </button>
        <h1 className="text-[18px] font-bold text-[#1A1A18] flex-1 text-center pr-10">مشخصات کتاب</h1>
      </div>

      <div className="flex-1 overflow-y-auto px-5 pb-4">
        {/* Cover */}
        <div className="flex justify-center mt-2 mb-6">
          <div className="flex flex-col items-center gap-3">
            <div
              className="w-24 h-32 rounded-xl shadow-lg"
              style={{ background: 'linear-gradient(160deg, #3D5A47 0%, #6B8F71 100%)' }}
            />
            <div className="flex gap-3">
              <button className="min-h-12 text-[12px] font-semibold text-[#2B5329] border border-[#2B5329] rounded-lg px-3 py-2 active:bg-[#EBF2EB]">
                تغییر جلد
              </button>
              <button className="min-h-12 text-[12px] font-semibold text-[#A84040] border border-[#A84040]/30 rounded-lg px-3 py-2 active:bg-[#FDECEA]">
                حذف جلد
              </button>
            </div>
          </div>
        </div>

        <div className="flex flex-col gap-4">
          <Field label="عنوان کتاب" value="جزیره سرگردانی" required />
          <Field label="نام نویسنده" value="سیمین دانشور" />
          <Field label="نام مترجم" value="" />
          <Field label="ناشر" value="انتشارات خوارزمی" />
          <Field label="سال انتشار" value="۱۳۴۸" />

          <div className="flex flex-col gap-1">
            <label className="text-[12px] font-semibold text-[#6B6358]">زبان</label>
            <div className="h-12 rounded-xl border border-[#DDD6CC] bg-[#FDFBF8] px-3 flex items-center justify-between">
              <span className="text-[14px] text-[#1A1A18]">فارسی</span>
              <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="#6B6358" strokeWidth="2">
                <path d="M6 9l6 6 6-6" strokeLinecap="round" strokeLinejoin="round" />
              </svg>
            </div>
          </div>

          <div className="flex flex-col gap-1">
            <label className="text-[12px] font-semibold text-[#6B6358]">جهت متن</label>
            <div className="flex gap-2">
              {['راست به چپ (RTL)', 'چپ به راست (LTR)'].map((label, i) => (
                <button
                  key={i}
                  className="flex-1 h-12 rounded-xl border text-[12px] font-medium"
                  style={{
                    background: i === 0 ? '#EBF2EB' : '#FDFBF8',
                    borderColor: i === 0 ? '#2B5329' : '#DDD6CC',
                    color: i === 0 ? '#2B5329' : '#6B6358',
                  }}
                >
                  {label}
                </button>
              ))}
            </div>
          </div>
        </div>
      </div>

      <div className="px-5 pb-2 shrink-0">
        <button
          onClick={() => navigate('structure-review')}
          className="w-full h-12 bg-[#2B5329] text-[#F7F3ED] rounded-xl text-[15px] font-semibold active:bg-[#3D7338]"
        >
          ادامه و بررسی ساختار
        </button>
      </div>
      <NavBar />
    </div>
  )
}
