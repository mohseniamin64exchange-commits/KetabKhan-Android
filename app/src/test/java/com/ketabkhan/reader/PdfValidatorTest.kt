package com.ketabkhan.reader

import com.ketabkhan.reader.util.PdfValidationResult
import com.ketabkhan.reader.util.PdfValidator
import org.junit.Assert.*
import org.junit.Test

class PdfValidatorTest {

    @Test
    fun `validateRawPdfInput returns Success for valid pdf info`() {
        val result = PdfValidator.validateRawPdfInput(
            uriString = "content://com.android.providers.media.documents/document/123",
            name = "my_book.pdf",
            sizeBytes = 2048576L,
            mimeType = "application/pdf"
        )

        assertTrue(result is PdfValidationResult.Success)
        val success = result as PdfValidationResult.Success
        assertEquals("my_book.pdf", success.pdfInfo.name)
        assertEquals(2048576L, success.pdfInfo.sizeBytes)
        assertEquals("application/pdf", success.pdfInfo.mimeType)
        assertTrue(success.pdfInfo.formattedSize.contains("مگابایت"))
    }

    @Test
    fun `validateRawPdfInput returns Error for blank uri`() {
        val result = PdfValidator.validateRawPdfInput(
            uriString = "",
            name = "book.pdf",
            sizeBytes = 1000L,
            mimeType = "application/pdf"
        )

        assertTrue(result is PdfValidationResult.Error)
        assertEquals("فایلی انتخاب نشده است.", (result as PdfValidationResult.Error).message)
    }

    @Test
    fun `validateRawPdfInput returns Error for non pdf file`() {
        val result = PdfValidator.validateRawPdfInput(
            uriString = "content://media/external/file/1",
            name = "image.png",
            sizeBytes = 1000L,
            mimeType = "image/png"
        )

        assertTrue(result is PdfValidationResult.Error)
        assertEquals("فایل انتخاب‌شده یک فایل PDF معتبر نیست.", (result as PdfValidationResult.Error).message)
    }

    @Test
    fun `validateRawPdfInput returns Error for zero size file`() {
        val result = PdfValidator.validateRawPdfInput(
            uriString = "content://media/external/file/2",
            name = "empty_book.pdf",
            sizeBytes = 0L,
            mimeType = "application/pdf"
        )

        assertTrue(result is PdfValidationResult.Error)
        assertEquals("فایل انتخاب‌شده خالی است یا حجم آن صفر می‌باشد.", (result as PdfValidationResult.Error).message)
    }

    @Test
    fun `validateRawPdfInput returns Error for negative size file`() {
        val result = PdfValidator.validateRawPdfInput(
            uriString = "content://media/external/file/3",
            name = "unknown_size_book.pdf",
            sizeBytes = -1L,
            mimeType = "application/pdf"
        )

        assertTrue(result is PdfValidationResult.Error)
        assertEquals("فایل انتخاب‌شده خالی است یا حجم آن صفر می‌باشد.", (result as PdfValidationResult.Error).message)
    }

    @Test
    fun `validateRawPdfInput returns Success with unknown MIME when name has pdf extension`() {
        val result = PdfValidator.validateRawPdfInput(
            uriString = "content://media/external/file/4",
            name = "book.pdf",
            sizeBytes = 1048576L,
            mimeType = null
        )

        assertTrue(result is PdfValidationResult.Success)
        val success = result as PdfValidationResult.Success
        assertEquals("book.pdf", success.pdfInfo.name)
        assertEquals("نامشخص", success.pdfInfo.mimeType)
    }

    @Test
    fun `validateRawPdfInput returns Error with unknown MIME when name does not have pdf extension`() {
        val result = PdfValidator.validateRawPdfInput(
            uriString = "content://media/external/file/5",
            name = "book.bin",
            sizeBytes = 1048576L,
            mimeType = ""
        )

        assertTrue(result is PdfValidationResult.Error)
        assertEquals("فایل انتخاب‌شده یک فایل PDF معتبر نیست.", (result as PdfValidationResult.Error).message)
    }

    @Test
    fun `formatFileSize formats KB and MB correctly with Persian digits`() {
        val formattedKb = PdfValidator.formatFileSize(512000L) // 500 KB
        assertTrue(formattedKb.contains("کیلو"))

        val formattedMb = PdfValidator.formatFileSize(5242880L) // 5 MB
        assertTrue(formattedMb.contains("مگا"))
    }
}
