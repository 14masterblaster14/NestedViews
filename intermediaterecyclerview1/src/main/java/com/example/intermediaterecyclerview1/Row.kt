package com.example.intermediaterecyclerview1

/**
 */
enum class RowType {

    YEAR,
    HOLIDAY,
    DAY
}

data class Row(var rowType: RowType, var year: String?, var holiday: String?, var day: DayData?)