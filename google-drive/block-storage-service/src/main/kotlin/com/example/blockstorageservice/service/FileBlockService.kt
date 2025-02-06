package com.example.blockstorageservice.service

import com.example.blockstorageservice.dto.ChunkedFileBlockDto
import com.example.blockstorageservice.entity.PepperDriveFileBlock
import com.example.blockstorageservice.repository.PepperDriveFileBlockRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

@Service
class FileBlockService(
    private val fileBlockRepository: PepperDriveFileBlockRepository,
) {
    companion object {
        const val CHUNK_SIZE = 2 * 1024 * 1024 // 1 kb * 1kb * 2 = 2mb
        const val STORAGE_PATH = "chunked_files"
    }

    @Transactional
    fun saveFileBlocks(fileBlocks: List<PepperDriveFileBlock>) {
        fileBlockRepository.saveAll(fileBlocks)
    }

    /*
    파일을 순서대로 잘라서 ByteArray로 변환
    IO 스레드 사용
     */
    suspend fun sliceFileIntoFileBlock(file: MultipartFile): List<ChunkedFileBlockDto> {
        val storageDir = File(STORAGE_PATH)
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }

        val chunks = mutableListOf<ChunkedFileBlockDto>()

        withContext(Dispatchers.IO) {
            var chunkNumber = 0
            file.inputStream.use { inputStream ->
                val buffer = ByteArray(CHUNK_SIZE)
                var bytesRead: Int

                // InputStream.read(buffer) -> CHUNK_SIZE만큼 inputStream에서 데이터를 읽어옴
                // .also -> 수신객체 그대로 반환 -> 읽어온 바이트 수가 -1 이면 파일이 없는 것
                while(inputStream.read(buffer).also { bytesRead = it } != -1) {
                    // 마지막 청크는 정확히 2mb가 아닐 수 있다.
                    val chunkData = if (bytesRead == CHUNK_SIZE) buffer else buffer.copyOf(bytesRead)

                    val chunkedFileBlockDto = ChunkedFileBlockDto(chunkData, UUID.randomUUID(), chunkNumber)
                    chunks.add(chunkedFileBlockDto)

                    chunkNumber += 1
                }
            }
        }

        return chunks
    }
}