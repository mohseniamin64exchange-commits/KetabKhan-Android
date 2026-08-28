interface IconProps {
  size?: number
  className?: string
}

const icon = (path: string, viewBox = '0 0 24 24') =>
  function Icon({ size = 24, className = '' }: IconProps) {
    return (
      <svg
        width={size}
        height={size}
        viewBox={viewBox}
        fill="none"
        stroke="currentColor"
        strokeWidth={1.8}
        strokeLinecap="round"
        strokeLinejoin="round"
        className={className}
        aria-hidden="true"
      >
        {path.split('|').map((d, i) => (
          <path key={i} d={d} />
        ))}
      </svg>
    )
  }

export const IcBook = icon('M4 19.5A2.5 2.5 0 0 1 6.5 17H20|M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z')
export const IcSearch = icon('M21 21l-4.35-4.35|M17 11A6 6 0 1 1 5 11a6 6 0 0 1 12 0z')
export const IcBookmark = icon('M19 21l-7-5-7 5V5a2 2 0 0 1 2-2h10a2 2 0 0 1 2 2z')
export const IcSettings = icon('M12 15a3 3 0 1 0 0-6 3 3 0 0 0 0 6z|M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1-2.83 2.83l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-4 0v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83-2.83l.06-.06A1.65 1.65 0 0 0 4.68 15a1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1 0-4h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 2.83-2.83l.06.06A1.65 1.65 0 0 0 9 4.68a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 4 0v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 2.83l-.06.06A1.65 1.65 0 0 0 19.4 9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 0 4h-.09a1.65 1.65 0 0 0-1.51 1z')
export const IcShare = icon('M4 12v8a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2v-8|M16 6l-4-4-4 4|M12 2v13')
export const IcDelete = icon('M3 6h18|M19 6l-1 14a2 2 0 0 1-2 2H8a2 2 0 0 1-2-2L5 6|M8 6V4a1 1 0 0 1 1-1h6a1 1 0 0 1 1 1v2')
export const IcEdit = icon('M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7|M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z')
export const IcMore = icon('M12 13a1 1 0 1 0 0-2 1 1 0 0 0 0 2z|M19 13a1 1 0 1 0 0-2 1 1 0 0 0 0 2z|M5 13a1 1 0 1 0 0-2 1 1 0 0 0 0 2z')
export const IcGrid = icon('M3 3h7v7H3z|M14 3h7v7h-7z|M3 14h7v7H3z|M14 14h7v7h-7z')
export const IcList = icon('M9 6h11|M9 12h11|M9 18h11|M5 6h.01|M5 12h.01|M5 18h.01')
export const IcSort = icon('M3 6h18|M7 12h10|M11 18h2')
export const IcAdd = icon('M12 5v14|M5 12h14')
export const IcImport = icon('M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4|M7 10l5 5 5-5|M12 15V3')
export const IcFile = icon('M13 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V9z|M13 2v7h7')
export const IcFolder = icon('M3 5a2 2 0 0 1 2-2h5l2 3h7a2 2 0 0 1 2 2v10a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5z')
export const IcLock = icon('M6 10V7a6 6 0 0 1 12 0v3|M5 10h14a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-8a2 2 0 0 1 2-2z|M12 15v3')
export const IcScan = icon('M3 8V5a2 2 0 0 1 2-2h3|M16 3h3a2 2 0 0 1 2 2v3|M21 16v3a2 2 0 0 1-2 2h-3|M8 21H5a2 2 0 0 1-2-2v-3|M7 12h10')
export const IcTable = icon('M3 4h18v16H3z|M3 9h18|M9 4v16|M15 4v16')
export const IcMoon = icon('M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z')
export const IcSun = icon('M12 1v2|M12 21v2|M4.22 4.22l1.42 1.42|M18.36 18.36l1.42 1.42|M1 12h2|M21 12h2|M4.22 19.78l1.42-1.42|M18.36 5.64l1.42-1.42|M12 5a7 7 0 1 0 0 14A7 7 0 0 0 12 5z')
export const IcBrightness = icon('M12 3v1|M12 20v1|M4.22 4.22l.71.71|M19.07 19.07l.71.71|M3 12h1|M20 12h1|M4.22 19.78l.71-.71|M19.07 4.93l.71-.71|M16 12a4 4 0 1 1-8 0 4 4 0 0 1 8 0z')
export const IcWarmth = icon('M12 2a5 5 0 0 1 5 5c0 2.76-5 11-5 11S7 9.76 7 7a5 5 0 0 1 5-5z|M12 9a2 2 0 1 0 0-4 2 2 0 0 0 0 4z')
export const IcToc = icon('M3 4h1|M7 4h14|M3 9h1|M7 9h14|M3 14h1|M7 14h10|M3 19h1|M7 19h8')
export const IcBackward = icon('M19 12H5|M12 5l7 7-7 7')
export const IcForward = icon('M5 12h14|M12 19l-7-7 7-7')
export const IcChevronLeft = icon('M15 18l-6-6 6-6')
export const IcChevronRight = icon('M9 18l6-6-6-6')
export const IcChevronDown = icon('M6 9l6 6 6-6')
export const IcCheck = icon('M20 6L9 17l-5-5')
export const IcWarning = icon('M10.29 3.86L1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z|M12 9v4|M12 17h.01')
export const IcX = icon('M18 6L6 18|M6 6l12 12')
export const IcBackup = icon('M20 7H4a2 2 0 0 0-2 2v10a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2z|M16 21V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16')
export const IcRestore = icon('M1 4v6h6|M3.51 15a9 9 0 1 0 .49-3.34')
export const IcImage = icon('M21 19V5a2 2 0 0 0-2-2H5a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2z|M8.5 10a1.5 1.5 0 1 0 0-3 1.5 1.5 0 0 0 0 3z|M21 15l-5-5L5 21')
export const IcInfo = icon('M12 22a10 10 0 1 0 0-20 10 10 0 0 0 0 20z|M12 8h.01|M11 12h1v4h1')
export const IcArrowRight = icon('M5 12h14|M12 5l7 7-7 7')
