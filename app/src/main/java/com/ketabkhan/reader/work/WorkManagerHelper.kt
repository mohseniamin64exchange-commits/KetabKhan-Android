package com.ketabkhan.reader.work

import android.content.Context
import androidx.work.*
import java.util.UUID

object WorkManagerHelper {

    fun scheduleBookProcessing(context: Context, fileUri: String): UUID {
        val inputData = Data.Builder()
            .putString(BookProcessingWorker.KEY_FILE_URI, fileUri)
            .build()

        val constraints = Constraints.Builder()
            .setRequiresStorageNotLow(true)
            .build()

        val request = OneTimeWorkRequestBuilder<BookProcessingWorker>()
            .setInputData(inputData)
            .setConstraints(constraints)
            .addTag("book_processing")
            .build()

        WorkManager.getInstance(context).enqueue(request)
        return request.id
    }

    fun observeBookProcessing(context: Context, workId: UUID): kotlinx.coroutines.flow.Flow<WorkInfo?> {
        return WorkManager.getInstance(context).getWorkInfoByIdFlow(workId)
    }
}
