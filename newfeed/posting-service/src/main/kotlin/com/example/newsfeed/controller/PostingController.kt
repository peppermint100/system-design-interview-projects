package com.example.newsfeed.controller

import com.example.newsfeed.dto.PostDto
import com.example.newsfeed.service.PostingService
import com.example.newsfeed.vo.CreatePostRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/posts")
class PostingController(
    val postingService: PostingService
) {

    @PostMapping
    fun createPost(@RequestBody request: CreatePostRequest): ResponseEntity<PostDto> {
        val postDto = postingService.createPost(request.userId, request.content)
        return ResponseEntity.status(HttpStatus.CREATED).body(postDto)
    }
}