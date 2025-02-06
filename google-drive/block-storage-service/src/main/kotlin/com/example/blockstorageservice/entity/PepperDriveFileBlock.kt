package com.example.blockstorageservice.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "file_block")
class PepperDriveFileBlock(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

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
            return PepperDriveFileBlock(file = fileEntity, order = order, createdAt = Instant.now())
        }
    }
}