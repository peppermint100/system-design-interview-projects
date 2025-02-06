package com.example.blockstorageservice.repository

import com.example.blockstorageservice.entity.PepperDriveFileBlock
import org.springframework.data.jpa.repository.JpaRepository

interface PepperDriveFileBlockRepository: JpaRepository<PepperDriveFileBlock, Long> {
}