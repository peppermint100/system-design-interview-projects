package com.example.newsfeed.service

import com.example.newsfeed.config.KafkaConfig
import com.example.newsfeed.dto.PostDto
import com.example.newsfeed.entity.Post
import com.example.newsfeed.repository.PostingRepository
import com.example.newsfeed.vo.CreatePostEvent
import com.example.newsfeed.vo.PostCache
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class PostingService(
    val postingRepository: PostingRepository,
    @Qualifier("createPostEventKafkaTemplate")
    val kafkaTemplate: KafkaTemplate<String, CreatePostEvent>,
    private val redisTemplate: RedisTemplate<String, String>
) {

    @Transactional
    fun createPost(userId: UUID, content: String): PostDto {
        // 1. Post 데이터 저장
        val postEntity = Post.create(userId, content)
        val savedPost = postingRepository.save(postEntity)

        // 2. Post 캐시 저장
        val redisKey = "post:${savedPost.id}"
        val postCache = PostCache.of(savedPost)
        redisTemplate.opsForHash<String, String>().putAll(redisKey, mapOf(
            "id" to postCache.id.toString(),
            "content" to postCache.content.toString(),
            "createdAt" to postCache.createdAt.epochSecond.toString()
        ))

        // 3. 메시지 큐로 Post 이벤트 발행
        val createPostEvents = CreatePostEvent.create(savedPost.id, userId)
        kafkaTemplate.send(KafkaConfig.topic, createPostEvents)

        return PostDto.of(savedPost)
    }

}