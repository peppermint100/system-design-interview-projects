package com.example.newsfeed.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "news_feeds")
class NewsFeed(
    @Id
    @Column(updatable = false, columnDefinition = "BINARY(16)")
    val id: UUID = UUID.randomUUID(),

    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    val userId: UUID,

    @Column(name = "", length = 5000)
    val content: String,

    @Column(name = "created_at")
    val createdAt: Instant = Instant.now()
) {
    companion object {
        fun create(userId: UUID, content: String): NewsFeed {
            return NewsFeed(
                userId = userId,
                content = content
            )
        }
    }
}