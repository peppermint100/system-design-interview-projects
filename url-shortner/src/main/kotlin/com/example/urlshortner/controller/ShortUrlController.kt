package com.example.urlshortner.controller

import com.example.urlshortner.dto.ShortUrlDto
import com.example.urlshortner.service.ShortUrlService
import com.example.urlshortner.vo.CreateShortUrlRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
class ShortUrlController(
    private val shortUrlService: ShortUrlService
) {

    @PostMapping("shorten")
    fun createShortUrl(@RequestBody request: CreateShortUrlRequest): ResponseEntity<ShortUrlDto> {
        val shortUrlDto = shortUrlService.generateShortUrl(request.originalUrl)
        return ResponseEntity.status(HttpStatus.CREATED).body(shortUrlDto)
    }

    @GetMapping("/{shortUrlKey}")
    fun redirectToOriginalUrl(@PathVariable shortUrlKey: String): ResponseEntity<Void> {
        val originalUrl = shortUrlService.getOriginalUrlBy(shortUrlKey)
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl)).build()
    }
}