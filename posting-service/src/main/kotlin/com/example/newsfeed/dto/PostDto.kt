package com.example.newsfeed.dto

import com.example.newsfeed.entity.Post
import java.time.Instant
import java.util.UUID

data class PostDto(
    val id: UUID,
    val userId: UUID,
    val content: String,
    val createdAt: Instant
) {
    companion object {
        fun of(entity: Post): PostDto {
            return PostDto(entity.id, entity.userId, entity.content, entity.createdAt)
        }
    }
}

