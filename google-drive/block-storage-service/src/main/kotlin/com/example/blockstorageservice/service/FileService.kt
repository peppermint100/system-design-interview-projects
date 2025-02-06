package com.example.blockstorageservice.service

import com.example.blockstorageservice.amazon.s3.S3Service
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
    private val s3Service: S3Service
) {

    @Transactional
    suspend fun uploadFile(file: MultipartFile, userId: Long) {
        val fileEntity = PepperDriveFile.create(file)
        val fileBlocks = fileBlockService.sliceFileIntoFileBlock(file)
        val fileVersion = PepperDriveFileVersion.create(fileEntity)

        val uploadedFileBlockDto = s3Service.upload(fileBlocks)
        val fileBlockEntities = uploadedFileBlockDto.map { fileBlock -> PepperDriveFileBlock.create(fileEntity, fileBlock) }

        saveFile(fileEntity, fileBlockEntities, fileVersion)
    }

    @Transactional
    fun saveFile(fileEntity: PepperDriveFile, fileBlockEntities: List<PepperDriveFileBlock>, fileVersionEntity: PepperDriveFileVersion) {
        fileRepository.save(fileEntity)
        fileVersionService.saveFileVersion(fileVersionEntity)
        fileBlockService.saveFileBlocks(fileBlockEntities)
    }
}