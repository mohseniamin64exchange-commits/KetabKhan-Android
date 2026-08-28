package com.example.data.repository

import com.example.data.db.BookDao
import com.example.data.db.BookmarkDao
import com.example.data.db.SettingsDao
import com.example.data.model.AppSettingsEntity
import com.example.data.model.BookEntity
import com.example.data.model.BookmarkEntity
import com.example.data.sample.SampleData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class BookRepository(
    private val bookDao: BookDao,
    private val bookmarkDao: BookmarkDao,
    private val settingsDao: SettingsDao
) {
    val allBooks: Flow<List<BookEntity>> = bookDao.getAllBooks()
    val allBookmarks: Flow<List<BookmarkEntity>> = bookmarkDao.getAllBookmarks()
    val settingsFlow: Flow<AppSettingsEntity?> = settingsDao.getSettingsFlow()

    suspend fun checkAndSeedDatabase() = withContext(Dispatchers.IO) {
        val books = SampleData.getSampleBooks()
        val firstBook = bookDao.getBookById("1")
        if (firstBook == null) {
            bookDao.insertBooks(books)
            SampleData.getSampleBookmarks().forEach { bookmark ->
                bookmarkDao.insertBookmark(bookmark)
            }
            if (settingsDao.getSettings() == null) {
                settingsDao.saveSettings(AppSettingsEntity())
            }
        }
    }

    fun getBookByIdFlow(id: String): Flow<BookEntity?> = bookDao.getBookByIdFlow(id)

    suspend fun getBookById(id: String): BookEntity? = withContext(Dispatchers.IO) {
        bookDao.getBookById(id)
    }

    suspend fun insertBook(book: BookEntity) = withContext(Dispatchers.IO) {
        bookDao.insertBook(book)
    }

    suspend fun updateBook(book: BookEntity) = withContext(Dispatchers.IO) {
        bookDao.updateBook(book)
    }

    suspend fun deleteBookById(id: String) = withContext(Dispatchers.IO) {
        bookDao.deleteBookById(id)
        bookmarkDao.deleteBookmarksForBook(id)
    }

    suspend fun updateReadingProgress(id: String, progress: Int, chapterIndex: Int, offset: Int, lastRead: String) = withContext(Dispatchers.IO) {
        bookDao.updateReadingProgress(id, progress, chapterIndex, offset, lastRead)
    }

    suspend fun updateBookStatus(id: String, status: String) = withContext(Dispatchers.IO) {
        bookDao.updateBookStatus(id, status)
    }

    // Bookmarks
    fun getBookmarksForBook(bookId: String): Flow<List<BookmarkEntity>> = bookmarkDao.getBookmarksForBook(bookId)

    suspend fun addBookmark(bookmark: BookmarkEntity): Long = withContext(Dispatchers.IO) {
        bookmarkDao.insertBookmark(bookmark)
    }

    suspend fun deleteBookmarkById(id: Long) = withContext(Dispatchers.IO) {
        bookmarkDao.deleteBookmarkById(id)
    }

    // Settings
    suspend fun saveSettings(settings: AppSettingsEntity) = withContext(Dispatchers.IO) {
        settingsDao.saveSettings(settings)
    }

    suspend fun getSettings(): AppSettingsEntity = withContext(Dispatchers.IO) {
        settingsDao.getSettings() ?: AppSettingsEntity()
    }
}
