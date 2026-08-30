package com.ketabkhan.reader.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.ketabkhan.reader.data.model.SelectedPdfInfo
import java.util.Locale

sealed class PdfValidationResult {
    data class Success(val pdfInfo: SelectedPdfInfo) : PdfValidationResult()
    data class Error(val message: String) : PdfValidationResult()
}

object PdfValidator {

    fun validateAndExtractInfo(context: Context, uri: Uri?): PdfValidationResult {
        if (uri == null || uri.toString().isBlank()) {
            return PdfValidationResult.Error("فایلی انتخاب نشده است.")
        }

        val contentResolver = context.contentResolver

        val name = getFileName(contentResolver, uri)
        val sizeBytes = getFileSize(contentResolver, uri)
        val rawMimeType = contentResolver.getType(uri)?.trim()

        val isPdfMime = !rawMimeType.isNullOrBlank() && (
            rawMimeType.equals("application/pdf", ignoreCase = true) || 
            rawMimeType.equals("application/x-pdf", ignoreCase = true)
        )
        val isPdfExtension = name.lowercase(Locale.ROOT).endsWith(".pdf")

        if (!isPdfMime && !isPdfExtension) {
            return PdfValidationResult.Error("فایل انتخاب‌شده یک فایل PDF معتبر نیست.")
        }

        if (sizeBytes <= 0L) {
            return PdfValidationResult.Error("فایل انتخاب‌شده خالی است یا حجم آن صفر می‌باشد.")
        }

        val isReadable = try {
            contentResolver.openInputStream(uri)?.use { true } ?: false
        } catch (e: Exception) {
            false
        }

        if (!isReadable) {
            return PdfValidationResult.Error("امکان خواندن فایل انتخاب‌شده وجود ندارد. لطفا دسترسی فایل را بررسی کنید.")
        }

        val formattedSize = formatFileSize(sizeBytes)
        val displayMimeType = if (rawMimeType.isNullOrBlank()) "نامشخص" else rawMimeType

        return PdfValidationResult.Success(
            SelectedPdfInfo(
                uriString = uri.toString(),
                name = name,
                sizeBytes = sizeBytes,
                formattedSize = formattedSize,
                mimeType = displayMimeType
            )
        )
    }

    fun getFileName(contentResolver: ContentResolver, uri: Uri): String {
        var name: String? = null
        if (uri.scheme == "content") {
            try {
                contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        if (index != -1) {
                            name = cursor.getString(index)
                        }
                    }
                }
            } catch (e: Exception) {
                // Ignore query exceptions
            }
        }
        if (name.isNullOrBlank()) {
            name = uri.lastPathSegment ?: "document.pdf"
        }
        return name
    }

    fun getFileSize(contentResolver: ContentResolver, uri: Uri): Long {
        var size: Long = -1
        if (uri.scheme == "content") {
            try {
                contentResolver.query(uri, arrayOf(OpenableColumns.SIZE), null, null, null)?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val index = cursor.getColumnIndex(OpenableColumns.SIZE)
                        if (index != -1 && !cursor.isNull(index)) {
                            size = cursor.getLong(index)
                        }
                    }
                }
            } catch (e: Exception) {
                // Ignore query exceptions
            }
        }
        return size
    }

    fun formatFileSize(bytes: Long): String {
        if (bytes <= 0) return "باید حجم مثبت باشد".toPersianDigits()
        val kb = bytes / 1024.0
        val mb = kb / 1024.0
        return if (mb >= 1.0) {
            String.format(Locale.US, "%.1f مگابایت", mb).toPersianDigits()
        } else if (kb >= 1.0) {
            String.format(Locale.US, "%.0f کیلوبایت", kb).toPersianDigits()
        } else {
            "$bytes بایت".toPersianDigits()
        }
    }

    fun validateRawPdfInput(uriString: String?, name: String?, sizeBytes: Long, mimeType: String?): PdfValidationResult {
        if (uriString.isNullOrBlank()) {
            return PdfValidationResult.Error("فایلی انتخاب نشده است.")
        }
        val safeName = name ?: ""
        val rawMime = mimeType?.trim()
        val isPdfMime = !rawMime.isNullOrBlank() && (
            rawMime.equals("application/pdf", ignoreCase = true) || 
            rawMime.equals("application/x-pdf", ignoreCase = true)
        )
        val isPdfExt = safeName.lowercase(Locale.ROOT).endsWith(".pdf")

        if (!isPdfMime && !isPdfExt) {
            return PdfValidationResult.Error("فایل انتخاب‌شده یک فایل PDF معتبر نیست.")
        }
        if (sizeBytes <= 0) {
            return PdfValidationResult.Error("فایل انتخاب‌شده خالی است یا حجم آن صفر می‌باشد.")
        }

        val displayMime = if (rawMime.isNullOrBlank()) "نامشخص" else rawMime

        return PdfValidationResult.Success(
            SelectedPdfInfo(
                uriString = uriString,
                name = if (safeName.isBlank()) "document.pdf" else safeName,
                sizeBytes = sizeBytes,
                formattedSize = formatFileSize(sizeBytes),
                mimeType = displayMime
            )
        )
    }

    private fun String.toPersianDigits(): String {
        val englishToPersianMap = mapOf(
            '0' to '۰', '1' to '۱', '2' to '۲', '3' to '۳', '4' to '۴',
            '5' to '۵', '6' to '۶', '7' to '۷', '8' to '۸', '9' to '۹'
        )
        return this.map { englishToPersianMap[it] ?: it }.joinToString("")
    }
}
