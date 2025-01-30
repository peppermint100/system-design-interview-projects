package com.example.urlshortner.util

import java.security.MessageDigest

object MD5Hash {

    // Always return 16 byte of hash
    fun hash(target: String): ByteArray {
        return MessageDigest.getInstance("MD5")
            .digest(target.toByteArray())
    }
}