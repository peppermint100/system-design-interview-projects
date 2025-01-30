package com.example.urlshortner

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UrlShortnerApplication

fun main(args: Array<String>) {
    println(System.currentTimeMillis())
    runApplication<UrlShortnerApplication>(*args)
}
