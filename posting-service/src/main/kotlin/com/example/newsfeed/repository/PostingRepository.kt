package com.example.newsfeed.repository

import com.example.newsfeed.entity.Post
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PostingRepository: JpaRepository<Post, UUID> {
}