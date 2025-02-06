package com.example.blockstorageservice.repository

import com.example.blockstorageservice.entity.PepperDriveFile
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PepperDriveFileRepository: JpaRepository<PepperDriveFile, UUID> {
}