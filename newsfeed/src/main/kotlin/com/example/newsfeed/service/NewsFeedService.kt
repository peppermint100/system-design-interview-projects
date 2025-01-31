package com.example.newsfeed.service

import com.example.newsfeed.dto.NewsFeedDto
import com.example.newsfeed.entity.NewsFeed
import com.example.newsfeed.repository.NewsFeedRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class NewsFeedService(
    val newsFeedRepository: NewsFeedRepository
) {

    @Transactional
    fun createNewsFeed(userId: UUID, content: String): NewsFeedDto {
        // 1. NewsFeed 데이터 저장
        val newsFeedEntity = NewsFeed.create(userId, content)
        val savedNewsFeed = newsFeedRepository.save(newsFeedEntity)

        // 2. 해당 유저의 친구목록 가져오기
        // 3. 메시지 큐로 NewsFeed 이벤트 발행

        return NewsFeedDto.of(savedNewsFeed)
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