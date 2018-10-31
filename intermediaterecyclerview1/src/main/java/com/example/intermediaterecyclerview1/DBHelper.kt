package com.example.intermediaterecyclerview1

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.json.JSONObject
import java.text.ParseException

import java.util.*

/**
 */
object DBHelper {

    val KEY_DATE = "date"
    val KEY_TEMP = "temp"

    fun getDaysFromJson(context: Context, fileName: String): ArrayList<Day> {

        val dataset = ArrayList<Day>()

        try {
            // Load the JSONArray from the file
            val jsonString = loadJsonFromFile(context, fileName)
            val json = JSONObject(jsonString)
            val jsondays = json.getJSONArray("days")

            // Create the list of movies
            for (index in 0 until jsondays.length()) {

                val day = jsondays.getJSONObject(index).getString(KEY_DATE)
                Log.i("masterBlaster", "day : ${day}")

                var localDate: LocalDate = LocalDate.parse(day)
                Log.i("masterBlaster", "localDate : ${localDate}")

                val temp = jsondays.getJSONObject(index).getInt(KEY_TEMP)
                Log.i("MasterBlaster", "temp : ${temp}")

                dataset.add(Day(day, temp.toString()))
            }

        } catch (e: Exception) {
            return dataset
        }
        return dataset
    }


    private fun loadJsonFromFile(context: Context, filename: String): String {

        var json = ""

        try {
            val input = context.assets.open(filename)
            val size = input.available()
            val buffer = ByteArray(size)
            input.read(buffer)
            input.close()
            json = buffer.toString(Charsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return json
    }
}