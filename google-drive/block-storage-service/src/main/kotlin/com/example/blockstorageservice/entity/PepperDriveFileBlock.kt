package com.example.blockstorageservice.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "file_block")
class PepperDriveFileBlock(
    @Id @Column(columnDefinition = "BINARY(16)", updatable = false)
    val id: UUID,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", columnDefinition = "BINARY(16)")
    val file: PepperDriveFile,

    @Column(name = "block_order")
    val order: Int,

    @Column(name = "created_at")
    val createdAt: Instant
) {

    companion object {
        fun create(fileEntity: PepperDriveFile, order: Int): PepperDriveFileBlock {
            return PepperDriveFileBlock(id = UUID.randomUUID(), file = fileEntity, order = order, createdAt = Instant.now())
        }
    }
}