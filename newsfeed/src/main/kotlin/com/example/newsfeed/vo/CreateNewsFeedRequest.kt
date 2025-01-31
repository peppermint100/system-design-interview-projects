package com.example.newsfeed.vo

import java.util.UUID

data class CreateNewsFeedRequest(
    val userId: UUID,
    val content: String
)
