package com.example.urlshortner.util

object Base62 {
    private const val ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
    private const val BASE = ALPHABET.length

    fun encode(num: Long): String {
        var number = num
        if (number == 0L) return ALPHABET[0].toString()

        val sb = StringBuilder()
        while (number > 0) {
            sb.append(ALPHABET[(number % BASE).toInt()])
            number /= BASE
        }

        return sb.reverse().toString()
    }
}
