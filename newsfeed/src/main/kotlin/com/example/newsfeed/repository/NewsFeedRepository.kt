package com.example.newsfeed.repository

import com.example.newsfeed.entity.NewsFeed
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface NewsFeedRepository: JpaRepository<NewsFeed, UUID> {
}