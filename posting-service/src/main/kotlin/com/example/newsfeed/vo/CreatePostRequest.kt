package com.example.newsfeed.vo

import java.util.UUID

data class CreatePostRequest(
    val userId: UUID,
    val content: String
)
