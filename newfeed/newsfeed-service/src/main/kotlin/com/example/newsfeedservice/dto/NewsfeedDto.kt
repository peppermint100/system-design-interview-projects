package com.example.newsfeedservice.dto

import java.time.Instant
import java.util.UUID

data class NewsfeedDto(
    val postId: UUID,
    val content: String,
    val createdAt: Instant
)
