package com.example.newsfeed.controller

import com.example.newsfeed.dto.NewsFeedDto
import com.example.newsfeed.service.NewsFeedService
import com.example.newsfeed.vo.CreateNewsFeedRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/newsfeed")
class NewsFeedController(
    val newsFeedService: NewsFeedService
) {

    @PostMapping
    fun createNewsFeed(@RequestBody request: CreateNewsFeedRequest): ResponseEntity<NewsFeedDto> {
        val newsFeedDto = newsFeedService.createNewsFeed(request.userId, request.content)
        return ResponseEntity.status(HttpStatus.CREATED).body(newsFeedDto)
    }
}