package com.example.newsfeed.vo

import java.util.UUID

data class CreatePostEvent(
    val postId: UUID,
    val userId: UUID
) {
    companion object {
        fun create(postId: UUID, userId: UUID): CreatePostEvent {
            return CreatePostEvent(postId, userId)
        }
    }
}