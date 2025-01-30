package com.example.urlshortner.repository

import com.example.urlshortner.entity.ShortUrl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ShortUrlRepository: JpaRepository<ShortUrl, String> {
    fun findByOriginalUrlHash(originalUrlHash: ByteArray): ShortUrl?

    @Query("SELECT s.originalUrl FROM ShortUrl s WHERE s.shortUrlKey = :shortKey")
    fun findOriginalUrlByShortUrlKey(shortKey: String): String?}