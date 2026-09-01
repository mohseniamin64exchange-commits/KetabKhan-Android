package com.ketabkhan.reader.domain.chapter

/**
 * Pure utility to detect whether a single line of text represents a chapter/section heading in Persian books.
 */
class ChapterHeadingDetector {

    companion object {
        private const val MAX_HEADING_LENGTH = 120

        // Regex patterns for Persian numbers, words, and chapter prefixes
        private val ORDINAL_OR_NUMBER_PATTERN = """(?:[0-9۰-۹]+|اول|دوم|سوم|چهارم|پنجم|ششم|هفتم|هشتم|نهم|دهم|یازدهم|دوازدهم|سیزدهم|چهاردهم|پانزدهم|شانزدهم|هفدهم|هجدهم|نوزدهم|بیستم|یک|دو|سه|چهار|پنج|شش|هفت|هشت|نه|ده|نخست|آخر|پایانی)"""

        // Specific standalone heading keywords (e.g. مقدمه, پیشگفتار, نتیجه‌گیری, ...)
        private val STANDALONE_HEADINGS = setOf(
            "مقدمه",
            "پیشگفتار",
            "پیش گفتار",
            "نتیجه گیری",
            "نتیجه‌گیری",
            "جمع بندی",
            "جمع‌بندی",
            "منابع",
            "منابع و مآخذ",
            "فهرست منابع",
            "ضمیمه",
            "ضمائم",
            "پیوست",
            "پیوست‌ها",
            "مقدمه مؤلف",
            "مقدمه مترجم",
            "سخن ناشر",
            "دیباچه"
        )

        // Regex for lines starting with "فصل" or "بخش" followed by a number/ordinal
        private val CHAPTER_PREFIX_REGEX = Regex(
            """^\s*(?:فصل|بخش|قسمت|گفتار)\s+""" + ORDINAL_OR_NUMBER_PATTERN + """(?:\s*[:\-–—\.]\s*.*|\s+.*)?${'$'}""",
            RegexOption.IGNORE_CASE
        )

        // Regex for standalone numbered chapter patterns like "فصل ۱", "بخش دوم", "فصل اول:"
        private val EXACT_CHAPTER_REGEX = Regex(
            """^\s*(?:فصل|بخش|قسمت|گفتار)\s+""" + ORDINAL_OR_NUMBER_PATTERN + """\s*${'$'}""",
            RegexOption.IGNORE_CASE
        )
    }

    /**
     * Returns true if the given line is likely a chapter or section heading.
     */
    fun isHeading(line: String?): Boolean {
        if (line == null) return false
        val trimmed = line.trim()
        if (trimmed.isEmpty() || trimmed.length > MAX_HEADING_LENGTH) {
            return false
        }

        // Clean trailing punctuation like colon or dashes for standalone comparison
        val normalizedStandalone = trimmed
            .replace(Regex("""[:\-–—\.]+$"""), "")
            .trim()

        if (STANDALONE_HEADINGS.contains(normalizedStandalone)) {
            return true
        }

        if (EXACT_CHAPTER_REGEX.matches(trimmed)) {
            return true
        }

        if (CHAPTER_PREFIX_REGEX.matches(trimmed)) {
            return true
        }

        return false
    }
}
