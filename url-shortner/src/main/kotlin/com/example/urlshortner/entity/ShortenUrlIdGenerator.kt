package com.example.urlshortner.entity

import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class ShortenUrlIdGenerator(
    @Value("spring.application.name") private val applicationName: String,
    @Value("\${server.id}") private val serverId: Int,
    private val redisTemplate: RedisTemplate<String, String>
) {
    companion object {
        private const val MAX_SEQUENCE = 1000
    }

    fun nextId(): String {
        val sequenceKey = "${applicationName}:${serverId}"
        // 분산 환경에서도 유니크 하도록 Redis를 이용한 시퀀스 생성
        val sequence = (redisTemplate.opsForValue()
            .increment(sequenceKey) ?: 0) % MAX_SEQUENCE

        return buildString {
            append(System.currentTimeMillis())
            append(serverId.toString().padStart(2, '0'))
            append(sequence.toString().padStart(3, '0'))
        }
    }
}