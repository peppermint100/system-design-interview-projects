package com.example.urlshortner.entity

import com.example.urlshortner.util.MD5Hash
import jakarta.persistence.*

@Entity
@Table(
    name = "short_url",
    indexes = [
        Index(name = "idx_url_hash", columnList = "original_url_hash", unique = true),
        Index(name = "idx_short_key", columnList = "short_url_key", unique = true)]
    )
class ShortUrl(
    @Id
    val id: String,

    @Column(name = "short_url_key", length = 10, unique = true)
    val shortUrlKey: String,

    @Column(name = "original_url", length = 255)
    val originalUrl: String,

    @Column(name = "original_url_hash", unique = true, columnDefinition = "BINARY(16)")
    val originalUrlHash: ByteArray,
) {
    companion object {
        fun create(id: String, shortUrlKey: String, originalUrl: String): ShortUrl {
            val originalUrlHash = MD5Hash.hash(originalUrl)
            return ShortUrl(id, shortUrlKey, originalUrl, originalUrlHash)
        }
    }
}
