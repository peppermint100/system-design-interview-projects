package com.example.blockstorageservice.dto

import java.util.UUID

data class ChunkedFileBlockDto(
    val fileBlock: ByteArray,
    val id: UUID,
    val order: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChunkedFileBlockDto

        if (!fileBlock.contentEquals(other.fileBlock)) return false
        if (id != other.id) return false
        return order == other.order
    }

    override fun hashCode(): Int {
        var result = fileBlock.contentHashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + order
        return result
    }
}