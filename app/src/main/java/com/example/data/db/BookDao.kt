package com.example.data.db

import androidx.room.*
import com.example.data.model.BookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    fun getAllBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE id = :id")
    suspend fun getBookById(id: String): BookEntity?

    @Query("SELECT * FROM books WHERE id = :id")
    fun getBookByIdFlow(id: String): Flow<BookEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books: List<BookEntity>)

    @Update
    suspend fun updateBook(book: BookEntity)

    @Query("UPDATE books SET progress = :progress, currentChapterIndex = :chapterIndex, currentScrollOffset = :offset, lastRead = :lastRead WHERE id = :id")
    suspend fun updateReadingProgress(id: String, progress: Int, chapterIndex: Int, offset: Int, lastRead: String)

    @Query("UPDATE books SET status = :status WHERE id = :id")
    suspend fun updateBookStatus(id: String, status: String)

    @Query("DELETE FROM books WHERE id = :id")
    suspend fun deleteBookById(id: String)

    @Query("DELETE FROM books")
    suspend fun deleteAllBooks()
}
