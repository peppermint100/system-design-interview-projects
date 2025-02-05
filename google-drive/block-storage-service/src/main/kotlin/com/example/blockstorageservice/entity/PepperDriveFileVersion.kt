package com.example.blockstorageservice.entity

import jakarta.persistence.*
import java.time.Instant

// 버전 삭제 및 수정이 불가능함을 원칙으로 설계
@Entity
@Table(name = "file_version")
class PepperDriveFileVersion(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    val file: PepperDriveFile,

    @Column(name = "version_number")
    val number: Int = 1,

    @Column(name = "created_at")
    val createdAt: Instant,

    @Column(name = "updated_at")
    var updatedAt: Instant
) {

    companion object {
        fun create(fileEntity: PepperDriveFile): PepperDriveFileVersion {
            val currentTimestamp = Instant.now()
            return PepperDriveFileVersion(file = fileEntity, createdAt = currentTimestamp, updatedAt = currentTimestamp)
        }
    }
}