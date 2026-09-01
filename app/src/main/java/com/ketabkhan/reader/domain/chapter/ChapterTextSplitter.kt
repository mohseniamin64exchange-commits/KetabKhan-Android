package com.ketabkhan.reader.domain.chapter

/**
 * Represents a single detected chapter with its title and extracted content.
 */
data class DetectedChapter(
    val title: String,
    val content: String
)

/**
 * Represents the structured result of splitting a full book's text into preamble and chapters.
 */
data class ChapterSplitResult(
    val preamble: String,
    val chapters: List<DetectedChapter>
)

/**
 * Splits extracted book text into preamble and chapters based on detected headings.
 */
class ChapterTextSplitter(
    private val headingDetector: ChapterHeadingDetector = ChapterHeadingDetector()
) {

    /**
     * Splits full raw text into preamble and individual chapters.
     */
    fun splitText(rawText: String?): ChapterSplitResult {
        if (rawText.isNullOrBlank()) {
            return ChapterSplitResult(
                preamble = "",
                chapters = emptyList()
            )
        }

        val lines = rawText.lines()
        val preambleLines = mutableListOf<String>()
        val chapters = mutableListOf<DetectedChapter>()

        var currentTitle: String? = null
        val currentContentLines = mutableListOf<String>()

        for (line in lines) {
            val trimmedLine = line.trim()
            if (headingDetector.isHeading(trimmedLine)) {
                // If we were already collecting a chapter, flush it
                if (currentTitle != null) {
                    chapters.add(
                        DetectedChapter(
                            title = currentTitle,
                            content = currentContentLines.joinToString("\n").trim()
                        )
                    )
                    currentContentLines.clear()
                }
                currentTitle = trimmedLine
            } else {
                if (currentTitle == null) {
                    preambleLines.add(line)
                } else {
                    currentContentLines.add(line)
                }
            }
        }

        // Flush the last chapter if exists
        if (currentTitle != null) {
            chapters.add(
                DetectedChapter(
                    title = currentTitle,
                    content = currentContentLines.joinToString("\n").trim()
                )
            )
        }

        return ChapterSplitResult(
            preamble = preambleLines.joinToString("\n").trim(),
            chapters = chapters
        )
    }
}
