package com.example.blockstorageservice.amazon.s3

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.example.blockstorageservice.dto.ChunkedFileBlockDto
import com.example.blockstorageservice.dto.UploadedFileBlockDto
import kotlinx.coroutines.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class S3Service(
    private val s3Client: AmazonS3
) {
    @Value("\${cloud.aws.s3.bucket}") private lateinit var bucket: String

    companion object {
        val BYTE_ARRAY_CONTENT_TYPE = "application/actet-stream"
    }

    /*
    Chunk된 파일을 S3에 업로드
    Order는 이미 ChunkedFileBlockDto에 있으므로 비동기 업로드
     */
    suspend fun upload(fileBlockDtos: List<ChunkedFileBlockDto>): List<UploadedFileBlockDto> {
        val savedFileBlocks = mutableListOf<UploadedFileBlockDto>()

        withContext(Dispatchers.IO) {
            fileBlockDtos.map { fileBlockDto ->
                launch {
                    val metadata = generateMetadata(fileBlockDto)
                    val filename = generateFilename(fileBlockDto)
                    val inputStream = fileBlockDto.fileBlock.inputStream()

                    val putObjectRequest = PutObjectRequest(bucket, filename, inputStream, metadata)

                    s3Client.putObject(putObjectRequest)

                    val s3ObjectUrl = s3Client.getUrl(bucket, filename).toString()
                    val uploadedFileBlockDto = UploadedFileBlockDto(fileBlockDto.id, s3ObjectUrl, fileBlockDto.order)
                    savedFileBlocks.add(uploadedFileBlockDto)
                }
            }.joinAll() // join을 통해 모든 비동기가 완료된 후 반환
        }

        return savedFileBlocks
    }

    private fun generateMetadata(fileBlockDto: ChunkedFileBlockDto): ObjectMetadata {
        return ObjectMetadata().apply {
            this.contentType = BYTE_ARRAY_CONTENT_TYPE
            this.contentLength = fileBlockDto.fileBlock.size.toLong()
        }
    }

    private fun generateFilename(fileBlockDto: ChunkedFileBlockDto): String {
        return "${fileBlockDto.id}-${fileBlockDto.order}"
    }
}