Design a complete, production-ready, high-fidelity Android mobile UI/UX for a Persian RTL e-book reader and PDF-to-e-book converter app.

CRITICAL SCOPE
- UI/UX design only. Do not generate code.
- Create an Android mobile application, not a website, tablet dashboard, or desktop product.
- All visible interface text must be Persian and correctly RTL.
- Use English only for internal Figma page, component, variant, and layer names when helpful.
- Temporary product name: «کتاب‌خوان». Keep branding replaceable because the final name and logo will be selected later.
- Do not add login, accounts, cloud library, subscriptions, store, social feed, AI chat, audiobook, text summarization, or unrelated features.
- The first version is focused on creating a structured e-book from PDF, keeping multiple books in a local library, reading them, importing/exporting the proprietary book format, and local backup/restore.

PRODUCT LOGIC — MUST BE PRESERVED
1. The app supports multiple books, not only one book.
2. The home screen is «کتابخانه من» and shows all stored books.
3. The user can create a new e-book from a text-based, scanned, or mixed PDF.
4. PDF content is converted into a structured, reflowable e-book, not displayed only as fixed page images.
5. Font size changes must reflow paragraphs and images without breaking structure, clipping content, or causing horizontal scrolling.
6. The conversion identifies metadata, introduction, chapters, subchapters, paragraphs, images, captions, tables, footnotes, references, glossary, index, and appendices where present.
7. The user reviews only uncertain conversion issues. High-confidence structure is accepted automatically; medium-confidence items are marked for review.
8. The original writing must not be rewritten automatically. Only extraction errors, line breaks, spacing, Persian/Arabic ی and ک differences, repeated headers/footers, and clear OCR errors may be corrected.
9. Before becoming a valid book, the converted result passes a health check and a final preview.
10. The original PDF may temporarily remain inside the app during the first version for comparison and correction.
11. The app must never automatically delete the user’s original PDF from Downloads or other public phone storage.
12. A later version may remove only the temporary internal copy after successful validation. Show this distinction clearly in storage information.
13. Basic processing may happen offline. Optional online enhancement may improve OCR or difficult sections only after clear user permission.
14. No server-side book library or cloud retrieval is required. Once created or imported, reading must work offline.
15. A completed book can be exported as one proprietary app-book file containing the entire book structure.
16. Another user can select «واردکردن کتاب», choose that proprietary file, validate it, and add the complete book to their own library.
17. Exported shareable books contain the cover, metadata, introduction, full text, chapters, subchapters, images, tables, footnotes, and table of contents.
18. A shareable book must not contain personal reading position, reading percentage, bookmarks, brightness, font settings, or reading history.
19. Backup is different from sharing a book. Personal backup may contain multiple books, reading positions, bookmarks, and app settings.
20. The app includes «حالت مطالعه شبانه» with separate controls for in-app brightness and color warmth. Describe it as a warmer, lower-blue-light reading appearance, not ultraviolet protection.

ANDROID UX PRINCIPLES
- Base phone frame: 360 × 800, adaptable to common Android phone sizes.
- Respect Android status bar, navigation area, display cutouts, and safe areas.
- Use Material 3 interaction principles while maintaining a distinctive calm reading identity.
- Use dp-like measurements and an 8-point spacing system.
- Minimum touch target: 48 × 48.
- Keep primary actions reachable and clear, especially on one-handed phone use.
- Support Android system back behavior and predictive-back expectations: back closes a sheet/dialog first, returns to the previous step second, and never unexpectedly loses conversion progress.
- Use standard Android patterns for file picking and the system share sheet.
- Long conversion must be represented as background work. The user may leave the screen while processing continues.
- Do not rely only on color for status. Combine color with icons and text.
- Provide accessible contrast in light, warm, dark, and night-reading modes.
- Avoid tiny text and ensure Persian glyphs, dots, and diacritics are never clipped.
- Directional icons must adapt to RTL. Universal icons such as search, bookmark, delete, settings, and play/pause must not be incorrectly mirrored.
- Use clear confirmation for destructive actions.
- Keyboard, focus, loading, disabled, pressed, selected, success, warning, and error states must be considered.
- Keep one primary action per screen and use progressive disclosure for secondary or advanced options.
- Preserve user work automatically when navigating away from multi-step creation.

FIGMA REQUIREMENTS
- Create editable Figma designs using Auto Layout for all major containers.
- Create reusable components and variants instead of detached repeated elements.
- Create color variables, typography styles, spacing variables, radius variables, and theme modes.
- Use clear, consistent layer naming.
- Organize frames in user-flow order.
- Use realistic Persian sample content and Persian numerals where natural.
- Use abstract editorial placeholder covers; do not use copyrighted commercial book covers.

VISUAL DIRECTION
Create a quiet, elegant, warm, minimal, focused reading experience.

Suggested visual character:
- Primary: deep forest green
- Light background: soft warm ivory
- Surface: warm white
- Night-reading accent: warm amber
- Dark reading surface: very dark charcoal, never pure black
- Primary text: dark charcoal
- Secondary text: muted warm gray
- Success: natural green
- Warning: muted amber
- Error: accessible muted red

Avoid bright saturated colors, heavy gradients, glassmorphism, decorative clutter, excessive shadows, excessive rounded cards, and a generic business-dashboard appearance.
Use soft 12–16 corner radii where appropriate and subtle elevation only for floating controls, dialogs, bottom sheets, and book cards.

TYPOGRAPHY
- Prefer Vazirmatn if available. Otherwise use a high-quality Persian font available in Figma.
- Never use a Latin-only font that renders Persian poorly.
- Create styles for Display, Screen Title, Section Title, Book Title, Reading Heading, Reading Body, UI Body, Button, Metadata, and Caption.
- Reading text must use generous line height and comfortable paragraph spacing.
- All reading content must be RTL and responsive.

DESIGN SYSTEM PAGE
Create a separate “Design System” page containing:

1. Theme/color variables:
- Light UI
- Warm reading
- Dark reading
- Night reading
- Background, surface, text, secondary text, border, primary, success, warning, and error

2. Typography styles for Persian RTL UI and reading text.

3. Spacing, radius, and elevation rules.

4. Reusable components with variants:
- Primary, secondary, text, and destructive buttons
- Icon button
- Floating action button
- Top app bar
- Bottom sheet
- Dialog
- Snackbar
- Toggle switch
- Slider
- Search field
- Text input and metadata field
- Linear and circular progress indicators
- Tabs and segmented controls
- Grid book card
- List book row
- Status badge
- Empty state
- Error state
- File information card
- Chapter tree row
- Bookmark row
- Search result row
- Setting row
- Validation checklist row
- Import confirmation card

5. Component states:
- Default
- Pressed
- Focused
- Selected
- Disabled
- Loading
- Error
- Success

6. Book item states:
- Ready to read
- Currently reading
- Processing
- Needs review
- Conversion failed
- Imported

ICONOGRAPHY
Use consistent simple Material-style outline icons for add, import, search, more, book, bookmark, table of contents, settings, share, backup, restore, delete, edit, moon, brightness, warmth, grid, list, sort, file, warning, success, previous chapter, next chapter, and RTL back navigation.

HIGH-FIDELITY SCREENS

01 — SPLASH
- Minimal replaceable logo
- Temporary name «کتاب‌خوان»
- Warm ivory background
- No unnecessary text

02 — EMPTY LIBRARY
Top app bar:
- «کتابخانه من»
- Search icon
- More menu

Empty state:
- Calm book illustration or simple book icon
- «هنوز کتابی در کتابخانه شما نیست»
- «می‌توانید از یک فایل PDF کتاب جدید بسازید یا یک کتاب آماده را وارد کنید.»
- Primary: «ساخت کتاب جدید»
- Secondary: «واردکردن کتاب»

03 — LIBRARY GRID
- App bar with «کتابخانه من», search, grid/list toggle, sort, and more
- “Continue reading” area at the top
- Two-column book-cover grid
- Each item shows cover, title, author, progress/status, and overflow menu
- Include ready, ۴۲٪ read, processing, and needs-review examples
- Primary extended FAB: «ساخت کتاب»
- Import remains easy to find

04 — LIBRARY LIST
- Same library in list mode
- Thumbnail, title, author, progress, last-read information, status, and overflow menu
- Sorting bottom sheet: «آخرین مطالعه»، «تاریخ افزودن»، «عنوان کتاب»، «نام نویسنده»

05 — SELECT PDF / CREATE BOOK
Title: «ساخت کتاب جدید»
- Large PDF file-selection area
- «فایل PDF کتاب را انتخاب کنید»
- Explain support for text-based, scanned, and mixed PDFs
- Primary: «انتخاب فایل PDF»

Selected-file state:
- File name, size, page count, detected type, detected language, and replace-file action
- Optional toggle: «بهبود پردازش با اینترنت»
- Privacy note: «در صورت نیاز، فقط بخش‌های دشوار برای اصلاح بهتر پردازش می‌شوند.»
- Bottom primary: «شروع تبدیل»

06 — CONVERSION IN PROGRESS
Title: «در حال ساخت کتاب»
- File/book name
- Overall percentage
- Large linear progress indicator
- Current active stage

Stages:
1. «بررسی فایل»
2. «استخراج متن»
3. «تشخیص تصاویر و پاورقی‌ها»
4. «تشخیص فصل‌ها»
5. «اصلاح ساختار»
6. «آماده‌سازی پیش‌نمایش»

- «می‌توانید از این صفحه خارج شوید؛ پردازش در پس‌زمینه ادامه پیدا می‌کند.»
- «رفتن به کتابخانه»
- Low-emphasis destructive action: «لغو پردازش»
- Also create the compact “processing” library-card state

07 — BOOK DETAILS EDITOR
Title: «مشخصات کتاب»
- Cover preview
- «تغییر جلد» and «حذف جلد»
- «عنوان کتاب» required
- «نام نویسنده»
- «نام مترجم»
- «ناشر»
- «سال انتشار»
- «زبان»
- «جهت متن»
- Bottom primary: «ادامه و بررسی ساختار»

08 — STRUCTURE REVIEW
Title: «بررسی ساختار کتاب»
- Number of detected chapters
- Number needing review
- Confidence legend: «مطمئن» and «نیازمند بررسی»
- RTL hierarchical chapter tree

Example structure:
- «پیشگفتار»
- «مقدمه»
- «فصل اول: آشنایی با موضوع»
- «۱ـ۱ مفاهیم اصلی»
- «۱ـ۲ کاربردها»
- «فصل دوم: ادامه بحث»
- «منابع»
- «پیوست‌ها»

Contextual actions:
- Edit title
- Change heading level
- Reorder
- Merge with previous section
- Remove heading status without deleting text

- Subtly mark uncertain items in amber
- Primary: «پیش‌نمایش کتاب»
- Secondary: «اصلاح موارد مشکوک»

09 — REVIEW A CONVERSION ISSUE
- Issue title such as «عنوان نامشخص»، «ترتیب ستون‌ها نیازمند بررسی است»، «کیفیت تشخیص متن پایین است» or «اتصال پاورقی مشخص نیست»
- Original PDF page preview
- Converted e-book text preview
- On mobile, use stacked views or a segmented control; do not force a cramped side-by-side layout
- Actions: «تأیید»، «ویرایش»، «نادیده گرفتن»
- Previous/next issue navigation adapted to RTL

10 — FINAL BOOK PREVIEW
- Review bar: «پیش‌نمایش نهایی»
- Remaining issue count if applicable
- Real reading layout containing chapter title, Persian paragraphs, image with caption, and footnote marker
- Primary: «تأیید و افزودن به کتابخانه»
- Secondary: «بازگشت و اصلاح»

11 — READER / DISTRACTION-FREE
- Default reading state has no visible app chrome
- Warm ivory reading background
- Persian chapter heading and well-spaced body paragraphs
- Footnote marker
- Responsive image fitting reading width
- No horizontal scrolling
- Represent reflowable content, not a fixed PDF screenshot

12 — READER / CONTROLS VISIBLE
Controls appear after tapping the center of the reading area.

Top bar:
- RTL back to library
- Current chapter title
- Search
- Bookmark
- More

Bottom controls:
- Reading progress based on content location, not only fixed page number
- Current percentage
- Previous/next section adapted to RTL
- Table of contents
- Reading settings

13 — BOOK NAVIGATION
Full-height bottom sheet or full screen with tabs:
- «فهرست»
- «نشانک‌ها»
- «جست‌وجو»

Table of contents:
- Hierarchical structure
- Current chapter highlighted

Bookmarks:
- Chapter name, excerpt, date added, and delete action

Search:
- Search field
- Results grouped by chapter
- Highlighted search term and excerpt
- Tap result to navigate to the exact content location

14 — READING SETTINGS
Use a bottom sheet over the reading screen so changes can be previewed instantly.

- Font family
- Font-size slider with small and large «الف» indicators
- Line spacing
- Paragraph spacing
- Page margins
- Text alignment: «راست‌چین» and «دوطرفه»
- Reading navigation: «ورق‌زدن» and «پیمایش عمودی»
- Toggle: «روشن نگه‌داشتن صفحه»

Quick themes:
- «روشن»
- «کرم»
- «تیره»

Night section:
- Toggle: «حالت مطالعه شبانه»
- Slider: «روشنایی»
- Slider: «گرمی رنگ»
- Show an active night-reading example with warmer amber tone and reduced visual brightness

15 — BOOK OPTIONS
Contextual bottom sheet:
- «بازکردن کتاب»
- «اطلاعات کتاب»
- «ساخت فایل کتاب»
- «اشتراک‌گذاری»
- «تغییر جلد و مشخصات»
- «حذف از کتابخانه»

Deletion confirmation must explain that removing the app copy does not delete the user’s original PDF stored elsewhere on the phone.

16 — EXPORT / BUILD APP BOOK FILE
Title: «ساخت فایل کتاب»
- Explain that one proprietary book file includes cover, metadata, introduction, full text, chapters, images, tables, footnotes, and table of contents
- Explain that personal reading data is excluded

Validation checklist:
- «ساختار کتاب سالم است»
- «فهرست مطالب بررسی شد»
- «تصاویر و پاورقی‌ها در دسترس‌اند»
- «فایل آماده اشتراک‌گذاری است»

Actions:
- «ذخیره در گوشی»
- «اشتراک‌گذاری»

Success state with a temporary extension:
- «نام‌کتاب.bookapp»
- Treat the extension as replaceable later

17 — IMPORT BOOK
Title: «واردکردن کتاب»
- Initial file-selection state with «انتخاب فایل کتاب»

After selection:
- Cover
- Title
- Author
- File size
- Number of chapters
- Format version
- File health status
- Primary: «افزودن به کتابخانه»

Duplicate dialog:
- «این کتاب از قبل در کتابخانه وجود دارد.»
- «جایگزینی نسخه قبلی»
- «نگهداری هر دو نسخه»
- «لغو»

Also create invalid-file and newer-format-version error states.

18 — BACKUP AND RESTORE
Title: «پشتیبان‌گیری و بازیابی»

Backup:
- «پشتیبان‌گیری از همه کتاب‌ها»
- «انتخاب کتاب‌ها»
- Personal-data choices: reading position, bookmarks, app settings
- Primary: «ساخت نسخه پشتیبان»

Restore:
- «انتخاب فایل پشتیبان»
- Preview contents before restoration
- Number of books and backup date
- Clearly explain merge/replacement behavior
- Primary: «بازیابی نسخه پشتیبان»

Create progress, success, conflict, and failure states.

19 — GENERAL SETTINGS
Title: «تنظیمات»

Sections:
- «نمایش کتابخانه»
- «تنظیمات پیش‌فرض مطالعه»
- «پردازش با اینترنت»
- «مدیریت فضای ذخیره‌سازی»
- «پشتیبان‌گیری و بازیابی»
- «درباره برنامه»

Include:
- Default grid/list view
- Default reading theme
- Default reading navigation
- Online enhancement toggle
- Storage usage summary
- Clear temporary-PDF information

Explicitly state:
- The original PDF selected from the phone is never automatically deleted.
- Only a temporary internal app copy may be removed after a validated conversion in a later version.

20 — STATUS, ERROR, AND PERMISSION STATES
Create a presentation frame with reusable examples for:
- Empty library
- Loading
- Conversion progress
- Conversion paused
- Conversion failed
- Needs review
- Import success
- Invalid proprietary book file
- Unsupported/newer format version
- Backup success
- Restore conflict
- No internet
- Optional online-processing consent
- Storage full
- Damaged PDF
- Password-protected PDF
- General confirmation dialog
- Snackbar feedback

For a password-protected PDF, show a secure password input and state that the password will not be stored.

IMPORTANT READING BEHAVIOR
- Changing font size is content reflow, not image zoom.
- Line wrapping, paragraph order, headings, images, captions, and footnotes remain structurally correct.
- Images scale to reading width and open in a full-screen zoom view when tapped.
- Simple tables may reflow; wide or complex tables use an independent horizontally scrollable container so the whole book never scrolls horizontally.
- Tapping a footnote marker opens a small readable overlay or bottom sheet, with an option to go to the full note.
- Preserve the exact reading location by content anchor, not only page number.
- Allow page-turning or vertical scrolling as a user preference.

PROTOTYPE PREPARATION
Arrange screens so these flows can be connected later:

Flow A:
Empty Library → Create New Book → Select PDF → Processing → Book Details → Structure Review → Issue Review → Final Preview → Add to Library → Reader

Flow B:
Library → Open Book → Reader → Table of Contents → Reading Settings → Night Reading Mode

Flow C:
Library → Import Book → Validate → Add to Library → Reader

Flow D:
Library → Book Options → Export Book → Android Share Sheet

Flow E:
Settings → Backup and Restore → Create Backup or Restore Backup

Do not create prototype connections if that would reduce the quality or completeness of the design. Prioritize reusable components, accurate Persian RTL, coherent screens, and correct states.

FINAL FIGMA ORGANIZATION
Create pages or clearly separated sections named:
1. Design System
2. Core Screens
3. Create Book
4. Reader
5. Import Export
6. Backup Settings
7. States

Name frames numerically:
01 Splash
02 Empty Library
03 Library Grid
04 Library List
05 Select PDF
and continue in order.

FINAL QUALITY CHECK
- The result must look like one coherent production-ready Persian Android app, not unrelated mockups.
- Confirm that every visible label is Persian and RTL.
- Confirm that Persian font rendering and line height are correct.
- Confirm that all primary actions and destructive actions are clear.
- Confirm that Android safe areas and 48 × 48 minimum touch targets are respected.
- Confirm that light, cream, dark, and night-reading appearances remain accessible.
- Confirm that no unrequested features were added.
- Confirm that exported books and personal backups remain conceptually separate.
- Confirm that the user’s original PDF is never represented as being automatically deleted.
