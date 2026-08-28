package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class BookStatus(val label: String, val colorHex: Long) {
    READING("در حال مطالعه", 0xFF2B5329),
    READY("آماده مطالعه", 0xFF3A7A3A),
    PROCESSING("در حال پردازش", 0xFFB87A28),
    REVIEW("نیاز به بررسی", 0xFFB87A28),
    FAILED("خطا در تبدیل", 0xFFA84040),
    IMPORTED("وارد شده", 0xFF2B5329)
}

data class Footnote(
    val id: String,
    val number: String,
    val text: String
)

data class Chapter(
    val id: String,
    val title: String,
    val level: Int = 0,
    val confident: Boolean = true,
    val content: String,
    val footnotes: List<Footnote> = emptyList(),
    val imageCaption: String? = null
)

data class ReviewIssue(
    val id: Int,
    val title: String,
    val desc: String,
    val original: String,
    val converted: String,
    val isResolved: Boolean = false
)

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val author: String,
    val translator: String = "",
    val publisher: String = "",
    val publishYear: String = "",
    val progress: Int = 0,
    val status: String = BookStatus.READY.name,
    val coverColor: String = "#3D5A47",
    val coverAccent: String = "#6B8F71",
    val lastRead: String? = null,
    val addedDate: String = "۱۴۰۳/۰۵/۱۶",
    val chaptersCount: Int = 0,
    val currentChapterIndex: Int = 0,
    val currentScrollOffset: Int = 0,
    val language: String = "فارسی",
    val direction: String = "RTL",
    val isFavorite: Boolean = false,
    val chaptersJson: String = "[]",
    val filePath: String? = null
)

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val bookId: String,
    val chapterTitle: String,
    val excerpt: String,
    val date: String,
    val chapterIndex: Int = 0,
    val scrollOffset: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "app_settings")
data class AppSettingsEntity(
    @PrimaryKey
    val id: Int = 1,
    val libraryViewMode: String = "grid", // "grid" or "list"
    val librarySortBy: String = "lastRead", // "lastRead", "addedDate", "title", "author"
    val defaultFont: String = "وزیرمتن",
    val defaultFontSizeSp: Float = 17f,
    val defaultLineHeight: Float = 2.0f,
    val defaultTextAlign: String = "justify",
    val defaultNavMode: String = "scroll",
    val defaultTheme: String = "light",
    val keepScreenOn: Boolean = false,
    val nightMode: Boolean = false,
    val nightBrightness: Float = 50f,
    val nightWarmth: Float = 50f,
    val onlineOcrConsent: Boolean = false
)

data class ReaderSettings(
    val font: String = "وزیرمتن",
    val fontSizeSp: Float = 17f,
    val lineHeightMultiplier: Float = 2.0f,
    val align: String = "justify", // "justify" or "right"
    val navMode: String = "scroll", // "scroll" or "page"
    val screenOn: Boolean = false,
    val theme: String = "light", // "light", "cream", "dark"
    val nightMode: Boolean = false,
    val nightBrightness: Float = 50f,
    val nightWarmth: Float = 50f
)
