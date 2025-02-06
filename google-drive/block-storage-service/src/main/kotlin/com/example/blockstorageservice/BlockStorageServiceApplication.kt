package com.example.blockstorageservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BlockStorageServiceApplication

fun main(args: Array<String>) {
	runApplication<BlockStorageServiceApplication>(*args)
}
