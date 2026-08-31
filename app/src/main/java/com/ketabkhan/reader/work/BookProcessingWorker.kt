package com.ketabkhan.reader.work

import android.content.Context
import android.net.Uri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.ketabkhan.reader.domain.pdf.PdfExtractResult
import com.ketabkhan.reader.domain.pdf.PdfTextExtractor
import java.io.File
import java.nio.charset.StandardCharsets

/**
 * WorkManager worker for handling long-running background document conversion and processing tasks.
 * In this version, architecture is established cleanly for phase 2 execution without simulated mock progress.
 */
class BookProcessingWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        const val KEY_FILE_URI = "key_file_uri"
        const val KEY_OUTPUT_NAME = "key_output_name"
        const val KEY_RESULT_STATUS = "key_result_status"
        const val KEY_ERROR_MESSAGE = "key_error_message"
        const val KEY_PAGE_COUNT = "key_page_count"
        const val KEY_TEXT_FILE_PATH = "key_text_file_path"
    }

    private fun saveExtractedText(text: String): String {
        val processingDir = File(applicationContext.filesDir, "pdf_processing").apply {
            if (!exists()) {
                mkdirs()
            }
        }
        val targetFile = File(processingDir, "$id.txt")
        targetFile.writeText(text, StandardCharsets.UTF_8)
        return targetFile.absolutePath
    }

    override suspend fun doWork(): Result {
        val fileUriString = inputData.getString(KEY_FILE_URI) ?: return Result.failure(
            workDataOf(KEY_ERROR_MESSAGE to "مسیر فایل نامعتبر است")
        )

        val uri = try {
            Uri.parse(fileUriString)
        } catch (e: Exception) {
            return Result.failure(
                workDataOf(
                    KEY_RESULT_STATUS to "failed",
                    KEY_ERROR_MESSAGE to "فرمت شناسه فایل نامعتبر است"
                )
            )
        }

        val extractor = PdfTextExtractor()
        return when (val extractResult = extractor.extractText(applicationContext, uri)) {
            is PdfExtractResult.Success -> {
                val savedFilePath = saveExtractedText(extractResult.extractedText)
                Result.success(
                    workDataOf(
                        KEY_RESULT_STATUS to "success",
                        KEY_PAGE_COUNT to extractResult.pageCount,
                        KEY_TEXT_FILE_PATH to savedFilePath
                    )
                )
            }
            is PdfExtractResult.NoExtractableText -> {
                Result.failure(
                    workDataOf(
                        KEY_RESULT_STATUS to "no_extractable_text",
                        KEY_PAGE_COUNT to extractResult.pageCount
                    )
                )
            }
            is PdfExtractResult.PasswordProtected -> {
                Result.failure(
                    workDataOf(
                        KEY_RESULT_STATUS to "password_protected"
                    )
                )
            }
            is PdfExtractResult.Failure -> {
                Result.failure(
                    workDataOf(
                        KEY_RESULT_STATUS to "failed",
                        KEY_ERROR_MESSAGE to extractResult.message
                    )
                )
            }
        }
    }
}
