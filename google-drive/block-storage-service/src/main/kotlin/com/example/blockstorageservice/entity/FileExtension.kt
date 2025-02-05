package com.example.blockstorageservice.entity

enum class FileExtension(val tailString: String) {
    NONE(""),
    PDF("pdf");

    companion object {
        fun getFrom(originalFilename: String): FileExtension {
            return entries.find {
                originalFilename.lowercase() == it.tailString
            } ?: NONE
        }
    }
}