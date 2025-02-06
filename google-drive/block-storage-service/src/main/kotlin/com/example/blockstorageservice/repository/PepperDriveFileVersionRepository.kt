package com.example.blockstorageservice.repository

import com.example.blockstorageservice.entity.PepperDriveFileVersion
import org.springframework.data.jpa.repository.JpaRepository

interface PepperDriveFileVersionRepository: JpaRepository<PepperDriveFileVersion, Long> {
}