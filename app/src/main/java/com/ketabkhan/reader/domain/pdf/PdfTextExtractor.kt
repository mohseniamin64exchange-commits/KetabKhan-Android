package com.ketabkhan.reader.domain.pdf

import android.content.Context
import android.net.Uri
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.encryption.InvalidPasswordException
import com.tom_roush.pdfbox.text.PDFTextStripper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

sealed interface PdfExtractResult {
    data class Success(
        val extractedText: String,
        val pageCount: Int
    ) : PdfExtractResult

    data class NoExtractableText(
        val pageCount: Int
    ) : PdfExtractResult

    data object PasswordProtected : PdfExtractResult

    data class Failure(
        val message: String,
        val cause: Throwable? = null
    ) : PdfExtractResult
}

class PdfTextExtractor {

    suspend fun extractText(context: Context, uri: Uri): PdfExtractResult = withContext(Dispatchers.IO) {
        try {
            // Ensure PDFBox resources are loaded for Android
            PDFBoxResourceLoader.init(context.applicationContext)

            val inputStream = context.contentResolver.openInputStream(uri)
                ?: return@withContext PdfExtractResult.Failure("امکان باز کردن فایل PDF وجود ندارد.")

            inputStream.use { stream ->
                try {
                    PDDocument.load(stream).use { document ->
                        if (document.isEncrypted) {
                            return@withContext PdfExtractResult.PasswordProtected
                        }

                        val pageCount = document.numberOfPages
                        val stripper = PDFTextStripper()
                        val rawText = stripper.getText(document)
                        val trimmedText = rawText?.trim().orEmpty()

                        if (trimmedText.isBlank()) {
                            PdfExtractResult.NoExtractableText(pageCount = pageCount)
                        } else {
                            PdfExtractResult.Success(
                                extractedText = trimmedText,
                                pageCount = pageCount
                            )
                        }
                    }
                } catch (e: InvalidPasswordException) {
                    PdfExtractResult.PasswordProtected
                }
            }
        } catch (e: SecurityException) {
            PdfExtractResult.Failure("دسترسی به فایل انتخاب‌شده مجاز نیست.", e)
        } catch (e: IOException) {
            PdfExtractResult.Failure("خطا در خواندن یا پردازش فایل PDF: ${e.localizedMessage ?: "خطای ناشناخته"}", e)
        } catch (e: Exception) {
            PdfExtractResult.Failure("خطای غیرمنتظره در استخراج متن: ${e.localizedMessage ?: "خطای ناشناخته"}", e)
        }
    }
}
