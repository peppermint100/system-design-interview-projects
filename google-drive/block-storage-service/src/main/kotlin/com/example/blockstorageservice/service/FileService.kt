package com.example.blockstorageservice.service

import com.example.blockstorageservice.entity.PepperDriveFile
import com.example.blockstorageservice.entity.PepperDriveFileBlock
import com.example.blockstorageservice.entity.PepperDriveFileVersion
import com.example.blockstorageservice.repository.PepperDriveFileRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class FileService(
    private val fileBlockService: FileBlockService,
    private val fileVersionService: FileVersionService,
    private val fileRepository: PepperDriveFileRepository,
) {

    @Transactional
    suspend fun uploadFile(file: MultipartFile, userId: Long) {
        val fileEntity = PepperDriveFile.create(file)
        val fileBlocks = fileBlockService.sliceFileIntoFileBlock(file, fileEntity)
        val fileVersion = PepperDriveFileVersion.create(fileEntity)
        val fileBlockEntities = fileBlocks.map { dto -> dto.fileBlockEntity }
        saveFile(fileEntity, fileBlockEntities, fileVersion)
    }

    @Transactional
    fun saveFile(fileEntity: PepperDriveFile, fileBlockEntities: List<PepperDriveFileBlock>, fileVersionEntity: PepperDriveFileVersion) {
        fileRepository.save(fileEntity)
        fileVersionService.saveFileVersion(fileVersionEntity)
        fileBlockService.saveFileBlocks(fileBlockEntities)
    }
}