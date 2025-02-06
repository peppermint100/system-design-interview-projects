package com.example.blockstorageservice.dto

import java.util.UUID


data class UploadedFileBlockDto(
    val fileBlockId: UUID,
    val objectUrl: String,
    val order: Int,
)