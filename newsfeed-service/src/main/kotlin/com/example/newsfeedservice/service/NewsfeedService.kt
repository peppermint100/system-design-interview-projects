package com.example.newsfeedservice.service

import com.example.newsfeedservice.dto.NewsfeedDto
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID

@Service
class NewsfeedService(
    @Qualifier("postRedisTemplate") val postRedisTemplate: RedisTemplate<String, String>,
    @Qualifier("newsFeedRedisTemplate") val newsFeedRedisTemplate: RedisTemplate<String, String>,
) {

    fun getNewsfeed(userId: UUID): List<NewsfeedDto> {
        // 1. 뉴스피드 캐시에서 불러올 포스트들 불러오기 userId로 쿼리
        val postKey = "newsfeed:$userId"
        val postIdSet = newsFeedRedisTemplate.opsForList().range(postKey, 0, -1) ?: emptyList()

        // 2. 불러온 PostId로 포스트 캐시에서 데이터들 불러와서 NewsfeedDto 생성
        return postRedisTemplate.execute { connection ->
            connection.openPipeline()

            postIdSet.map { postId ->
                val key = "post:$postId"
                connection.hashCommands().hGetAll(key.toByteArray())
            }

            val results = connection.closePipeline()

            results.mapNotNull { result ->
                val hashData = result as? Map<ByteArray, ByteArray> ?: return@mapNotNull null

                // ByteArray를 String으로 변환
                val map = hashData.entries.associate { (key, value) ->
                    String(key) to String(value)
                }

                NewsfeedDto(
                    UUID.fromString(map["id"]),
                    map["content"] ?: "",
                    Instant.ofEpochMilli(map["createdAt"]?.toLong() ?: 0L)
                )
            }
        } ?: emptyList()
    }
}