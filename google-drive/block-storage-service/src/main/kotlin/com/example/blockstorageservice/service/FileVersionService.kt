package com.example.blockstorageservice.service

import com.example.blockstorageservice.entity.PepperDriveFileVersion
import com.example.blockstorageservice.repository.PepperDriveFileVersionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FileVersionService(
    private val fileVersionRepository: PepperDriveFileVersionRepository,
) {

    @Transactional
    fun saveFileVersion(fileVersion: PepperDriveFileVersion) {
        fileVersionRepository.save(fileVersion)
    }
}