package com.example.newsfeed.service

import com.example.newsfeed.config.KafkaConfig
import com.example.newsfeed.dto.NewsFeedDto
import com.example.newsfeed.entity.NewsFeed
import com.example.newsfeed.repository.NewsFeedRepository
import com.example.newsfeed.vo.CreateNewsFeedEvent
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class NewsFeedService(
    val newsFeedRepository: NewsFeedRepository,
    @Qualifier("createNewsFeedEventKafkaTemplate")
    val kafkaTemplate: KafkaTemplate<String, CreateNewsFeedEvent>
) {

    @Transactional
    fun createNewsFeed(userId: UUID, content: String): NewsFeedDto {
        // 1. NewsFeed 데이터 저장
        val newsFeedEntity = NewsFeed.create(userId, content)
        val savedNewsFeed = newsFeedRepository.save(newsFeedEntity)

        // 2. 메시지 큐로 NewsFeed 이벤트 발행
        val createNewsFeedEvents = CreateNewsFeedEvent.create(savedNewsFeed.id, userId)
        kafkaTemplate.send(KafkaConfig.topic, createNewsFeedEvents)

        return NewsFeedDto.of(savedNewsFeed)
    }

}