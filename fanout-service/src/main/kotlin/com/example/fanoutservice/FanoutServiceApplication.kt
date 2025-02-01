package com.example.fanoutservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FanoutServiceApplication

fun main(args: Array<String>) {
    runApplication<FanoutServiceApplication>(*args)
}
