package com.example.newsfeed.vo

import com.example.newsfeed.entity.Post
import java.time.Instant
import java.util.UUID

data class PostCache(
    val id: UUID,
    val content: String,
    val createdAt: Instant
) {
    companion object {
        fun of(entity: Post): PostCache {
            return PostCache(entity.id, entity.content, entity.createdAt)
        }
    }
}
