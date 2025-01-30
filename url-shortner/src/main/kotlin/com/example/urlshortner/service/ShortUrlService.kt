package com.example.urlshortner.service

import com.example.urlshortner.dto.ShortUrlDto
import com.example.urlshortner.entity.ShortUrl
import com.example.urlshortner.entity.ShortenUrlIdGenerator
import com.example.urlshortner.repository.ShortUrlRepository
import com.example.urlshortner.util.Base62
import com.example.urlshortner.util.MD5Hash
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.NoSuchElementException

@Service
class ShortUrlService(
    @Value("\${server.domain}") val domain: String,
    private val shortUrlRepository: ShortUrlRepository,
    private val idGenerator: ShortenUrlIdGenerator
) {

    @Transactional
    fun generateShortUrl(originalUrl: String): ShortUrlDto {
        val originalUrlHash = MD5Hash.hash(originalUrl)
        val existingUrl = shortUrlRepository.findByOriginalUrlHash(originalUrlHash)

        return when {
            // 이전에 이미 생성된 적이 있는 URL이면 따로 생성하지 않고 기존 값을 재활용
            existingUrl != null -> ShortUrlDto(domain + existingUrl.shortUrlKey)

            else -> {
                val id = idGenerator.nextId()
                // Base62로 URL의 키를 생성
                val shortUrlKey = Base62.encode(id.toLong())
                val shortUrl = domain + shortUrlKey
                val shortUrlEntity = ShortUrl.create(id, shortUrlKey, originalUrl)
                shortUrlRepository.save(shortUrlEntity)
                return ShortUrlDto(shortUrl)
            }
        }
    }

    fun getOriginalUrlBy(shortUrlKey: String): String {
        return shortUrlRepository.findOriginalUrlByShortUrlKey(shortUrlKey)
            ?: throw NoSuchElementException("존재하지 않는 단축 URL입니다.")
    }
}