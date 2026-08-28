import { useState } from 'react'
import StatusBar from '../components/StatusBar'
import NavBar from '../components/NavBar'
import { IcChevronLeft, IcBackup, IcRestore, IcCheck, IcWarning } from '../components/Icons'
import type { NavProps } from '../types'

export default function Backup({ navigate, goBack }: NavProps) {
  const [tab, setTab] = useState<'backup' | 'restore'>('backup')
  const [includePos, setIncludePos] = useState(true)
  const [includeBookmarks, setIncludeBookmarks] = useState(true)
  const [includeSettings, setIncludeSettings] = useState(false)
  const [backupDone, setBackupDone] = useState(false)
  const [restoreStep, setRestoreStep] = useState<'idle' | 'preview' | 'conflict' | 'done'>('idle')

  return (
    <div className="flex flex-col h-full bg-[#F7F3ED]" dir="rtl">
      <StatusBar />

      <div className="flex items-center px-2 h-14 shrink-0">
        <button onClick={goBack} className="w-12 h-12 flex items-center justify-center rounded-full text-[#1A1A18]">
          <IcChevronLeft size={24} />
        </button>
        <h1 className="text-[18px] font-bold text-[#1A1A18] flex-1 text-center pr-10">پشتیبان‌گیری و بازیابی</h1>
      </div>

      {/* Tabs */}
      <div className="flex border-b border-[#EDE8E0] shrink-0">
        {(['backup', 'restore'] as const).map(t => (
          <button
            key={t}
            onClick={() => setTab(t)}
            className="flex-1 h-12 text-[13px] font-medium relative"
            style={{ color: tab === t ? '#2B5329' : '#6B6358' }}
          >
            {t === 'backup' ? 'پشتیبان‌گیری' : 'بازیابی'}
            {tab === t && <div className="absolute bottom-0 inset-x-0 h-0.5 bg-[#2B5329]" />}
          </button>
        ))}
      </div>

      <div className="flex-1 overflow-y-auto px-5 py-4">
        {tab === 'backup' && (
          <>
            {backupDone ? (
              <div className="flex flex-col items-center gap-4 pt-8">
                <div className="w-16 h-16 rounded-full bg-[#EBF2EB] flex items-center justify-center">
                  <IcCheck size={32} className="text-[#2B5329]" />
                </div>
                <p className="text-[17px] font-bold text-[#2B5329]">پشتیبان ساخته شد</p>
                <div className="bg-[#FDFBF8] border border-[#DDD6CC] rounded-xl px-4 py-3 w-full text-center">
                  <p className="text-[13px] font-mono text-[#1A1A18]">ketabkhan-backup-۱۴۰۳۰۵۱۶.bak</p>
                  <p className="text-[12px] text-[#6B6358] mt-0.5">۴ کتاب · ۸.۳ مگابایت</p>
                </div>
                <button onClick={() => setBackupDone(false)} className="text-[13px] text-[#2B5329]">پشتیبان جدید</button>
              </div>
            ) : (
              <>
                <p className="text-[14px] font-semibold text-[#1A1A18] mb-4">انتخاب محتوا</p>

                {/* Include options */}
                <div className="bg-[#FDFBF8] rounded-2xl border border-[#DDD6CC] overflow-hidden mb-5">
                  {[
                    { label: 'همه کتاب‌ها', value: true, disabled: true },
                    { label: 'موقعیت مطالعه', value: includePos, setter: setIncludePos },
                    { label: 'نشانک‌ها', value: includeBookmarks, setter: setIncludeBookmarks },
                    { label: 'تنظیمات برنامه', value: includeSettings, setter: setIncludeSettings },
                  ].map(({ label, value, setter, disabled }, i) => (
                    <div key={i} className="flex items-center justify-between px-5 py-4 border-b border-[#EDE8E0] last:border-b-0">
                      <span className="text-[14px] text-[#1A1A18]">{label}</span>
                      <button
                        disabled={disabled}
                        onClick={() => setter?.(!value)}
                        className={`w-12 h-7 rounded-full transition-colors relative ${value ? 'bg-[#2B5329]' : 'bg-[#DDD6CC]'} ${disabled ? 'opacity-50' : ''}`}
                      >
                        <div className={`w-5 h-5 rounded-full bg-white shadow-sm absolute top-1 transition-all ${value ? 'right-1' : 'left-1'}`} />
                      </button>
                    </div>
                  ))}
                </div>
              </>
            )}
          </>
        )}

        {tab === 'restore' && (
          <>
            {restoreStep === 'idle' && (
              <div className="flex flex-col items-center gap-5 pt-8">
                <div className="w-20 h-20 rounded-3xl bg-[#EBF2EB] flex items-center justify-center">
                  <IcRestore size={36} className="text-[#2B5329]" />
                </div>
                <p className="text-[14px] text-[#6B6358] text-center leading-relaxed">
                  فایل پشتیبان با پسوند .bak را انتخاب کنید.
                </p>
                <button onClick={() => setRestoreStep('preview')} className="h-12 px-6 bg-[#2B5329] text-white rounded-xl text-[15px] font-semibold active:bg-[#3D7338]">
                  انتخاب فایل پشتیبان
                </button>
              </div>
            )}

            {restoreStep === 'preview' && (
              <>
                <div className="bg-[#FDFBF8] rounded-2xl border border-[#DDD6CC] p-4 mb-4">
                  <p className="text-[14px] font-bold text-[#1A1A18] mb-3">محتوای فایل پشتیبان</p>
                  {[['نام فایل', 'ketabkhan-backup-۱۴۰۳۰۵۱۵.bak'], ['تاریخ ساخت', '۱۴۰۳/۰۵/۱۵'], ['تعداد کتاب‌ها', '۳'], ['حجم', '۶.۱ مگابایت']].map(([k, v]) => (
                    <div key={k} className="flex justify-between py-1.5 border-b border-[#EDE8E0] last:border-b-0">
                      <span className="text-[12px] text-[#6B6358]">{k}</span>
                      <span className="text-[12px] text-[#1A1A18] font-medium">{v}</span>
                    </div>
                  ))}
                </div>
                <div className="bg-[#FDF3E7] border border-[#F0D8B0] rounded-xl p-3 mb-4 flex items-start gap-2">
                  <IcWarning size={18} className="text-[#B87A28] shrink-0 mt-0.5" />
                  <p className="text-[12px] text-[#B87A28] leading-relaxed">
                    کتاب‌های موجود با نسخه پشتیبان ادغام می‌شوند. کتاب‌های تکراری جایگزین می‌شوند.
                  </p>
                </div>
              </>
            )}

            {restoreStep === 'conflict' && (
              <div className="bg-[#FDF3E7] border border-[#F0D8B0] rounded-2xl p-5 mb-4">
                <div className="flex items-center gap-3 mb-3">
                  <IcWarning size={22} className="text-[#B87A28]" />
                  <p className="text-[15px] font-bold text-[#B87A28]">تعارض در بازیابی</p>
                </div>
                <p className="text-[13px] text-[#6B6358] leading-relaxed mb-3">کتاب «جزیره سرگردانی» در پشتیبان و کتابخانه هر دو وجود دارد. کدام نسخه نگهداری شود؟</p>
                <div className="flex flex-col gap-2">
                  <button onClick={() => setRestoreStep('done')} className="w-full h-12 bg-[#2B5329] text-white rounded-xl text-[13px] font-semibold">نسخه پشتیبان (قدیمی‌تر)</button>
                  <button onClick={() => setRestoreStep('done')} className="w-full h-12 bg-[#FDFBF8] border border-[#DDD6CC] text-[#1A1A18] rounded-xl text-[13px] font-medium">نسخه فعلی (جدیدتر)</button>
                </div>
              </div>
            )}

            {restoreStep === 'done' && (
              <div className="flex flex-col items-center gap-4 pt-8">
                <div className="w-16 h-16 rounded-full bg-[#EBF2EB] flex items-center justify-center">
                  <IcCheck size={32} className="text-[#2B5329]" />
                </div>
                <p className="text-[17px] font-bold text-[#2B5329]">بازیابی کامل شد</p>
                <p className="text-[13px] text-[#6B6358]">۳ کتاب بازیابی شدند</p>
                <button onClick={() => navigate('library-grid')} className="h-12 px-8 bg-[#2B5329] text-white rounded-xl text-[15px] font-semibold">رفتن به کتابخانه</button>
              </div>
            )}
          </>
        )}
      </div>

      {(tab === 'backup' && !backupDone) && (
        <div className="px-5 pb-2 shrink-0">
          <button onClick={() => setBackupDone(true)} className="w-full h-12 bg-[#2B5329] text-white rounded-xl text-[15px] font-semibold active:bg-[#3D7338]">
            ساخت نسخه پشتیبان
          </button>
        </div>
      )}
      {(tab === 'restore' && restoreStep === 'preview') && (
        <div className="px-5 pb-2 shrink-0 flex flex-col gap-2">
          <button onClick={() => setRestoreStep('conflict')} className="w-full h-12 bg-[#2B5329] text-white rounded-xl text-[15px] font-semibold">بازیابی نسخه پشتیبان</button>
          <button onClick={() => setRestoreStep('idle')} className="w-full h-12 text-[#6B6358] text-[14px]">لغو</button>
        </div>
      )}

      <NavBar />
    </div>
  )
}
