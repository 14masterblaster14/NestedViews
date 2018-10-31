package com.example.intermediaterecyclerview.models

/**
 */
enum class RowType {
    PHOTO,
    HEADER
}

data class PhotoRow(var type: RowType, var photo: Photo?, var header: String?)