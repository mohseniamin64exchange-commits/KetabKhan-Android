package com.ketabkhan.reader.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class BookStatus(val titleFa: String) {
    READY("آماده مطالعه"),
    READING("در حال خواندن"),
    PROCESSING("در حال تبدیل"),
    REVIEW("نیازمند بررسی"),
    IMPORTED("وارد شده"),
    FAILED("خطا در تبدیل")
}

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey val id: String,
    val title: String,
    val author: String,
    val translator: String = "",
    val publisher: String = "",
    val publishYear: String = "",
    val progress: Int = 0,
    val status: String = BookStatus.READY.name,
    val coverColor: String = "#2B5329",
    val coverAccent: String = "#4A7C47",
    val lastRead: String? = null,
    val addedDate: String = "",
    val chaptersCount: Int = 0,
    val currentChapterIndex: Int = 0,
    val currentScrollOffset: Int = 0,
    val language: String = "فارسی",
    val direction: String = "RTL",
    val isFavorite: Boolean = false,
    val chaptersJson: String = "[]"
)

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val bookId: String,
    val chapterTitle: String,
    val excerpt: String,
    val date: String,
    val chapterIndex: Int,
    val scrollOffset: Int
)

@Entity(tableName = "app_settings")
data class AppSettingsEntity(
    @PrimaryKey val id: Int = 1,
    val fontSize: Float = 18f,
    val lineSpacing: Float = 1.6f,
    val paragraphSpacing: Float = 12f,
    val theme: String = "light", // "light", "sepia", "dark"
    val nightIntensity: Float = 0.5f,
    val textAlignment: String = "justify",
    val readingDirection: String = "RTL",
    val autoScroll: Boolean = false,
    val pageTransition: String = "scroll", // "scroll" or "slide"
    val showFootnotesInline: Boolean = false
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

data class Footnote(
    val id: String,
    val number: String,
    val text: String
)

data class ReviewIssue(
    val id: Int,
    val title: String,
    val desc: String,
    val original: String,
    val converted: String,
    val isResolved: Boolean = false
)

data class ReaderSettings(
    val fontSize: Float = 18f,
    val lineSpacing: Float = 1.6f,
    val paragraphSpacing: Float = 12f,
    val theme: String = "light",
    val nightIntensity: Float = 0.5f,
    val textAlignment: String = "justify",
    val readingDirection: String = "RTL",
    val autoScroll: Boolean = false,
    val pageTransition: String = "scroll",
    val showFootnotesInline: Boolean = false,
    val font: String = "وزیرمتن",
    val screenOn: Boolean = true
)
