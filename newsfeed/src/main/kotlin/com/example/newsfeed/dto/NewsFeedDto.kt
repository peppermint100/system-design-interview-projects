package com.example.newsfeed.dto

import com.example.newsfeed.entity.NewsFeed
import java.time.Instant
import java.util.UUID

data class NewsFeedDto(
    val id: UUID,
    val userId: UUID,
    val content: String,
    val createdAt: Instant
) {
    companion object {
        fun of(entity: NewsFeed): NewsFeedDto {
            return NewsFeedDto(entity.id, entity.userId, entity.content, entity.createdAt)
        }
    }
}

