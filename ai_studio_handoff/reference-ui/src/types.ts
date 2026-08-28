export type ScreenName =
  | 'splash'
  | 'library-empty'
  | 'library-grid'
  | 'library-list'
  | 'select-pdf'
  | 'conversion'
  | 'book-details'
  | 'structure-review'
  | 'issue-review'
  | 'final-preview'
  | 'reader-clean'
  | 'reader-controls'
  | 'book-nav'
  | 'reading-settings'
  | 'book-options'
  | 'export-book'
  | 'import-book'
  | 'backup'
  | 'settings'
  | 'states'

export interface NavProps {
  navigate: (screen: ScreenName) => void
  goBack: () => void
}

export interface Book {
  id: string
  title: string
  author: string
  progress: number
  status: 'ready' | 'reading' | 'processing' | 'review' | 'failed' | 'imported'
  coverColor: string
  coverAccent: string
  lastRead?: string
  addedDate: string
  chapters?: number
}

export const SAMPLE_BOOKS: Book[] = [
  {
    id: '1',
    title: 'جزیره سرگردانی',
    author: 'سیمین دانشور',
    progress: 42,
    status: 'reading',
    coverColor: '#3D5A47',
    coverAccent: '#6B8F71',
    lastRead: 'دیروز',
    addedDate: '۱۴۰۳/۰۵/۱۲',
    chapters: 18,
  },
  {
    id: '2',
    title: 'صد سال تنهایی',
    author: 'گابریل گارسیا مارکز',
    progress: 0,
    status: 'review',
    coverColor: '#5A3D3D',
    coverAccent: '#8F6B6B',
    lastRead: undefined,
    addedDate: '۱۴۰۳/۰۵/۱۵',
    chapters: 20,
  },
  {
    id: '3',
    title: 'بوف کور',
    author: 'صادق هدایت',
    progress: 0,
    status: 'processing',
    coverColor: '#3D3D5A',
    coverAccent: '#6B6B8F',
    lastRead: undefined,
    addedDate: '۱۴۰۳/۰۵/۱۶',
    chapters: undefined,
  },
  {
    id: '4',
    title: 'سووشون',
    author: 'سیمین دانشور',
    progress: 100,
    status: 'ready',
    coverColor: '#4A5A3D',
    coverAccent: '#7A8F6B',
    lastRead: 'یک هفته پیش',
    addedDate: '۱۴۰۳/۰۴/۰۸',
    chapters: 22,
  },
]
