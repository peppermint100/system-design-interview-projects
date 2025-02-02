package com.example.newsfeedservice.repository

import com.example.newsfeedservice.dto.NewsfeedDto
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.UUID

@Repository
class PostCacheRepository(
    @Qualifier("postRedisTemplate") val postRedisTemplate: RedisTemplate<String, String>,
) {
    fun hGetAllByPostId(postIdList: List<String>): List<NewsfeedDto> {
        return postRedisTemplate.execute { connection ->
            // 1번의 커넥션으로 여러 개의 데이터를 가져올 수 있도록 파이프라인 이용
            connection.openPipeline()

            postIdList.map { postId ->
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