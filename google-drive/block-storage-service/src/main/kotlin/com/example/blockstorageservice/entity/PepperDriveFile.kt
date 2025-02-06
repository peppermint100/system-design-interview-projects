package com.example.blockstorageservice.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.web.multipart.MultipartFile
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "file")
class PepperDriveFile(
    @Id @Column(columnDefinition = "BINARY(16)", updatable = false) val id: UUID,
    @Column(name = "file_name")  var filename: String,
    @Column(name = "extension") var extension: FileExtension,
    @Column(name = "file_size_byte") val fileSizeByte: Long,
    @Column(name = "created_at") val createdAt: Instant,
    @Column(name = "updated_at") var updatedAt: Instant,
) {

    companion object {
        fun create(file: MultipartFile): PepperDriveFile {
            val id = UUID.randomUUID()
            val fullFilename = file.originalFilename ?: "$id"
            val filename = fullFilename.substringBeforeLast(".")
            val extensionString = fullFilename.substringAfterLast(".", "")
            val extension = FileExtension.getFrom(extensionString)
            val currentTimestamp = Instant.now()
            return PepperDriveFile(
                id, filename, extension, file.size, currentTimestamp, currentTimestamp
            )
        }
    }

    fun getFullFilename(): String {
        return "${this.filename}.${this.extension.tailString}"
    }
}