package com.example.newsfeedservice.repository

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class NewsfeedCacheRepository(
    @Qualifier("newsFeedRedisTemplate") val newsFeedRedisTemplate: RedisTemplate<String, String>,
) {

    fun findListByUserId(userId: UUID): List<String> {
        val key = "newsfeed:$userId"
        return newsFeedRedisTemplate.opsForList().range(key, 0, -1) ?: emptyList()
    }
}