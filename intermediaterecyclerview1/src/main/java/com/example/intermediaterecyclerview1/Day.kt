package com.example.intermediaterecyclerview1

import org.joda.time.LocalDate
import java.util.*

/**
 */
data class Day(var date: String, var temperature: String)
/*: Comparable<Day>
{
override fun compareTo(other: Day): Int {

    //val localDate : LocalDate = LocalDate.parse("yyyy-MM-dd")
    return ((LocalDate.parse(this.date)).compareTo(LocalDate.parse(other.date)))
}
}*/