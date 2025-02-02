package com.example.newsfeedservice.service

import com.example.newsfeedservice.dto.NewsfeedDto
import com.example.newsfeedservice.repository.NewsfeedCacheRepository
import com.example.newsfeedservice.repository.PostCacheRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class NewsfeedService(
    private val newsfeedCacheRepository: NewsfeedCacheRepository,
    private val postCacheRepository: PostCacheRepository
) {

    fun getNewsfeed(userId: UUID): List<NewsfeedDto> {
        // 1. 뉴스피드 캐시에서 불러올 포스트들 불러오기 userId로 쿼리
        val postIdList = newsfeedCacheRepository.findListByUserId(userId)

        // 2. 불러온 PostId로 포스트 캐시에서 데이터들 불러와서 NewsfeedDto 생성
        return postCacheRepository.hGetAllByPostId(postIdList)
    }
}