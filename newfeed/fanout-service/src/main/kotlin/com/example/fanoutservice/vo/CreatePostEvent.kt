package com.example.fanoutservice.vo

import java.util.UUID

data class CreatePostEvent(
    val postId: UUID,
    val userId: UUID
)
