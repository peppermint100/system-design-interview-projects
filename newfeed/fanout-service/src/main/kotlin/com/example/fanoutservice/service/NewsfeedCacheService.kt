package com.example.fanoutservice.service

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class NewsfeedCacheService(
    private val redisTemplate: RedisTemplate<String, String>
) {

    fun cachePostToNewsfeed(userId: UUID, postId: UUID) {
        val friendIds = this.getFriendsIdFor(userId)
        friendIds.forEach { friendId ->
            val key = "newsfeed:$friendId"

            redisTemplate.opsForList()
                .leftPush(key, postId.toString())

            // 가장 최신 40개 포스트만 뉴스피드 캐시에 유지하도록 trim
            redisTemplate.opsForList()
                .trim(key, 0, 40)
        }
    }

    // 친구 목록은 더미를 사용
    private fun getFriendsIdFor(userId: UUID): List<UUID>  {
        return mutableListOf(
            "8f4e2a1d-9b67-4c35-8d12-3a9ef5b87c0e",
            "2c7d6b9a-4f83-4e15-b952-1d8fc4a6e0d3",
            "5a3b8c1e-7d24-4f96-a438-9e2d5c7b1f0a",
            "1f9e4d2b-3c85-4a67-b194-6d8e2c5f9b7d",
            "7b5a3c8d-2e94-4f16-8a73-5c9b4e2d1f0e",
            "4d2e8f1b-9c73-4a56-b841-3e7d5c9a2f0b",
            "3a9b6c2d-5e84-4f27-9c51-2d8e4b7a3f0c",
            "6e2d4f1b-8a95-4c37-b623-9f8a4c5d2e0b",
            "9c4b2e7d-1f83-4a56-b947-3e8d5c9a2f0d",
            "2f8e4d1b-7c93-4a56-b841-5e9d3c7a2f0e"
        ).map { UUID.fromString(it) }
    }
}