package com.ketabkhan.reader.ui.state

sealed interface PdfProcessingState {
    data object Idle : PdfProcessingState
    data object Queued : PdfProcessingState
    data object Running : PdfProcessingState

    data class Success(
        val pageCount: Int,
        val textFilePath: String
    ) : PdfProcessingState

    data class NoExtractableText(
        val pageCount: Int
    ) : PdfProcessingState

    data object PasswordProtected : PdfProcessingState

    data class Failed(
        val message: String
    ) : PdfProcessingState

    data object Cancelled : PdfProcessingState
}
