import { useState } from 'react'
import type { ScreenName } from './types'

import Splash from './screens/Splash'
import LibraryEmpty from './screens/LibraryEmpty'
import LibraryGrid from './screens/LibraryGrid'
import LibraryList from './screens/LibraryList'
import SelectPDF from './screens/SelectPDF'
import Conversion from './screens/Conversion'
import BookDetails from './screens/BookDetails'
import StructureReview from './screens/StructureReview'
import IssueReview from './screens/IssueReview'
import FinalPreview from './screens/FinalPreview'
import Reader from './screens/Reader'
import BookNav from './screens/BookNav'
import ReadingSettings from './screens/ReadingSettings'
import BookOptions from './screens/BookOptions'
import ExportBook from './screens/ExportBook'
import ImportBook from './screens/ImportBook'
import Backup from './screens/Backup'
import Settings from './screens/Settings'
import States from './screens/States'

export default function App() {
  const [screen, setScreen] = useState<ScreenName>('splash')
  const [history, setHistory] = useState<ScreenName[]>([])

  const navigate = (to: ScreenName) => {
    setHistory(h => [...h, screen])
    setScreen(to)
  }

  const goBack = () => {
    setHistory(h => {
      if (h.length === 0) return h
      const prev = h[h.length - 1]
      setScreen(prev)
      return h.slice(0, -1)
    })
  }

  const navProps = { navigate, goBack }

  const renderScreen = () => {
    switch (screen) {
      case 'splash': return <Splash {...navProps} />
      case 'library-empty': return <LibraryEmpty {...navProps} />
      case 'library-grid': return <LibraryGrid {...navProps} />
      case 'library-list': return <LibraryList {...navProps} />
      case 'select-pdf': return <SelectPDF {...navProps} />
      case 'conversion': return <Conversion {...navProps} />
      case 'book-details': return <BookDetails {...navProps} />
      case 'structure-review': return <StructureReview {...navProps} />
      case 'issue-review': return <IssueReview {...navProps} />
      case 'final-preview': return <FinalPreview {...navProps} />
      case 'reader-clean': return <Reader {...navProps} initialControls={false} />
      case 'reader-controls': return <Reader {...navProps} initialControls />
      case 'book-nav': return <BookNav {...navProps} />
      case 'reading-settings': return <ReadingSettings {...navProps} />
      case 'book-options': return <BookOptions {...navProps} />
      case 'export-book': return <ExportBook {...navProps} />
      case 'import-book': return <ImportBook {...navProps} />
      case 'backup': return <Backup {...navProps} />
      case 'settings': return <Settings {...navProps} />
      case 'states': return <States {...navProps} />
    }
  }

  return (
    <div
      className="min-h-screen flex flex-col items-center justify-start md:justify-center"
      style={{ background: '#D6CFC4', fontFamily: 'Vazirmatn, Tahoma, sans-serif' }}
    >
      {/* Responsive phone preview. Native Android must use real system insets. */}
      <div
        className="relative w-full max-w-[430px] bg-[#F7F3ED] overflow-hidden shadow-2xl flex flex-col"
        style={{ height: '100dvh', maxHeight: '932px', borderRadius: 'clamp(0px, calc((100vh - 800px) / 2), 36px)' }}
      >
        <div className="flex-1 overflow-hidden flex flex-col relative">
          {renderScreen()}
        </div>
      </div>
    </div>
  )
}
