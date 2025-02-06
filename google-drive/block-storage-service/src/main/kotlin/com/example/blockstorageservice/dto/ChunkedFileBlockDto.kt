package com.example.blockstorageservice.dto

import com.example.blockstorageservice.entity.PepperDriveFileBlock
import java.io.File

data class ChunkedFileBlockDto(
    val fileBlockEntity: PepperDriveFileBlock,
    val fileBlock: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChunkedFileBlockDto

        if (fileBlockEntity != other.fileBlockEntity) return false
        return fileBlock.contentEquals(other.fileBlock)
    }

    override fun hashCode(): Int {
        var result = fileBlockEntity.hashCode()
        result = 31 * result + fileBlock.contentHashCode()
        return result
    }
}
