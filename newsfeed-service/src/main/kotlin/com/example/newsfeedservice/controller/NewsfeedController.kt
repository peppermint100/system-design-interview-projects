package com.example.newsfeedservice.controller

import com.example.newsfeedservice.dto.NewsfeedDto
import com.example.newsfeedservice.service.NewsfeedService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/newsfeeds")
class NewsfeedController(
    val newsfeedService: NewsfeedService
) {

    @GetMapping
    fun getNewsfeeds(): ResponseEntity<List<NewsfeedDto>> {
        val userId = UUID.fromString("1f9e4d2b-3c85-4a67-b194-6d8e2c5f9b7d")
        val newsfeed = newsfeedService.getNewsfeed(userId)
        return ResponseEntity.status(HttpStatus.OK).body(newsfeed)
    }
}