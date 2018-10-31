package com.example.stickyheaderswipelayout

/**
 */
data class Row(var rowType: RowType, var year: HeaderYear?, var holiday: String?, var day: DayData?)

enum class RowType {

    YEAR,
    HOLIDAY,
    DAY
}
