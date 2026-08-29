package com.ketabkhan.reader.data.repository

import com.ketabkhan.reader.data.db.BookDao
import com.ketabkhan.reader.data.db.BookmarkDao
import com.ketabkhan.reader.data.db.SettingsDao
import com.ketabkhan.reader.data.model.AppSettingsEntity
import com.ketabkhan.reader.data.model.BookEntity
import com.ketabkhan.reader.data.model.BookmarkEntity
import com.ketabkhan.reader.data.preferences.ReaderPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class BookRepository(
    private val bookDao: BookDao,
    private val bookmarkDao: BookmarkDao,
    private val settingsDao: SettingsDao,
    val preferencesRepository: ReaderPreferencesRepository
) {
    val allBooks: Flow<List<BookEntity>> = bookDao.getAllBooks()
    val allBookmarks: Flow<List<BookmarkEntity>> = bookmarkDao.getAllBookmarks()
    val settingsFlow: Flow<AppSettingsEntity?> = settingsDao.getSettingsFlow()

    suspend fun checkAndSeedDatabase() = withContext(Dispatchers.IO) {
        // In normal app execution, user library starts empty (Requirement 11).
        // Default app settings are initialized if not present.
        if (settingsDao.getSettings() == null) {
            settingsDao.saveSettings(AppSettingsEntity())
        }
    }

    fun getBookByIdFlow(id: String): Flow<BookEntity?> = bookDao.getBookByIdFlow(id)

    suspend fun getBookById(id: String): BookEntity? = withContext(Dispatchers.IO) {
        bookDao.getBookById(id)
    }

    suspend fun insertBook(book: BookEntity) = withContext(Dispatchers.IO) {
        bookDao.insertBook(book)
    }

    suspend fun insertBooks(books: List<BookEntity>) = withContext(Dispatchers.IO) {
        bookDao.insertBooks(books)
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
