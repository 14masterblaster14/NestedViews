package com.example.nestedview.favourite

/**
 */

enum class RowType {

    PROGRESS_VIEW,
    LIST_VIEW,
    CARD_VIEW

}

data class FavouriteMainRow(var rowType: RowType, var mProgress: ArrayList<Season>?, var mList: ArrayList<String>?, var mCard: ArrayList<Card>?)