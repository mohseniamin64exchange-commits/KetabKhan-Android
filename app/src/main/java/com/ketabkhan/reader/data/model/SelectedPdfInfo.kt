package com.ketabkhan.reader.data.model

data class SelectedPdfInfo(
    val uriString: String,
    val name: String,
    val sizeBytes: Long,
    val formattedSize: String,
    val mimeType: String
)
