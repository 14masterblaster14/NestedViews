package com.example.charts

import android.content.Context
import android.util.Log
import org.json.JSONObject

object DBHelper {
    val KEY_DATE = "date"
    val KEY_TEMP = "temp"

    fun getDaysFromJson(context: Context, fileName: String): ArrayList<GraphPointModel> {

        val dataset = ArrayList<GraphPointModel>()

        try {
            // Load the JSONArray from the file
            val jsonString = loadJsonFromFile(context, fileName)
            val json = JSONObject(jsonString)
            val jsondays = json.getJSONArray("graphPoints")

            // Create the list of days
            for (index in 0 until jsondays.length()) {

                val day = jsondays.getJSONObject(index).getString(KEY_DATE)
                Log.i("masterBlaster", "day : ${day}")

                //var localDate : LocalDate = LocalDate.parse(day)
                //Log.i("masterBlaster","localDate : ${localDate}")

                val temp = jsondays.getJSONObject(index).getInt(KEY_TEMP)
                Log.i("MasterBlaster", "temp : ${temp}")

                dataset.add(GraphPointModel(day.toString(), temp.toString()))
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