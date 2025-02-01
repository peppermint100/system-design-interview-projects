package com.example.fanoutservice.kafka

import com.example.fanoutservice.service.NewsfeedCacheService
import com.example.fanoutservice.vo.CreatePostEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class CreatePostEventConsumer(
    private val newsfeedCacheService: NewsfeedCacheService
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    companion object {
        const val topic = "create_post_event"
    }

    @KafkaListener(
        topics = [topic],
        groupId = "post-consumer-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    fun consumeCreatePostEvent(event: CreatePostEvent) {
        try {
            logger.info("포스트 생성 이벤트를 감지했습니다 ${event.postId}, ${event.userId}")
            newsfeedCacheService.cachePostToNewsfeed(event.userId, event.postId)
        } catch (e: Exception) {
            logger.error("포스트 생성 이벤트를 소비하는데 실패했습니다 ${event.postId}")
        }
    }
}