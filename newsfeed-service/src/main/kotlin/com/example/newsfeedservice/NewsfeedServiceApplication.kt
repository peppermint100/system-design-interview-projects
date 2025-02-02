package com.example.newsfeedservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NewsfeedServiceApplication

fun main(args: Array<String>) {
    runApplication<NewsfeedServiceApplication>(*args)
}
