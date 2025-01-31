package com.example.newsfeed.vo

import java.util.UUID

data class CreateNewsFeedEvent(
    val postId: UUID,
    val userId: UUID
) {
    companion object {
        fun create(postId: UUID, userId: UUID): CreateNewsFeedEvent {
            return CreateNewsFeedEvent(postId, userId)
        }
    }
}