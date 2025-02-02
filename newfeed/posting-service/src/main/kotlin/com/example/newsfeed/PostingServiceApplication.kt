package com.example.newsfeed

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PostingServiceApplication

fun main(args: Array<String>) {
    runApplication<PostingServiceApplication>(*args)
}
