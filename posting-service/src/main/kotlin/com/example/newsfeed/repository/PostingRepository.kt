package com.example.newsfeed.repository

import com.example.newsfeed.entity.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PostingRepository: JpaRepository<Post, UUID> {
}