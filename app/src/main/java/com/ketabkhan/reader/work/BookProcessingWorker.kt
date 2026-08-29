package com.ketabkhan.reader.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

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
    }

    override suspend fun doWork(): Result {
        val fileUri = inputData.getString(KEY_FILE_URI) ?: return Result.failure(
            workDataOf(KEY_ERROR_MESSAGE to "مسیر فایل نامعتبر است")
        )

        // Architecture prepared for background PDF/Book processing in Phase 2.
        // Returns failure/pending notice because processing engine is not yet implemented.
        return Result.failure(
            workDataOf(
                KEY_RESULT_STATUS to "in_development",
                KEY_ERROR_MESSAGE to "موتور پردازش در این مرحله هنوز فعال نشده است"
            )
        )
    }
}
