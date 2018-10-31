package com.example.intermediaterecyclerview1

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.joda.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity(), RowClickListener {

    private var rowClickListener: RowClickListener = this
    private var dataset: ArrayList<Row>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val calenderDays: ArrayList<Day> = DBHelper.getDaysFromJson(this, "calender_days.json")

        Log.i("MasterBlaster", "dataSet :-> ${calenderDays}")
        //sortDataset(calenderDays)

        titleView.text = "temperature"
        recyclerView.layoutManager = LinearLayoutManager(this)
        val mAdapter = Adapter(this, getDataset(calenderDays), rowClickListener)
        recyclerView.adapter = mAdapter
        recyclerView.hasFixedSize()
        //dataset = getDataset(sortDataset(calenderDays))
    }


    private fun getDataset(calenderDays: ArrayList<Day>): ArrayList<Row> {
        var rowList = ArrayList<Row>()
        var isFirstElement = true
        var mPreviousYear: LocalDate.Property? = null
        //var mTempList = mutableListOf<Day_temp>()
        //var sortedDataset = ArrayList<Day_temp>()
        //val localDate : LocalDate = LocalDate.parse("yyyy-MM-dd")
        for (day in calenderDays) {


            var temp_Date: LocalDate = LocalDate.parse(day.date)
            Log.i("MasterBlaster", " Converted date :-> ${temp_Date}")


            if ((isFirstElement) || (mPreviousYear != temp_Date.year())) {
                val row = Row(RowType.YEAR, temp_Date.year.toString(), null, null)
                rowList.add(row)

                isFirstElement = false
            }


            var mRowType = RowType.DAY

            var mDate = temp_Date.dayOfMonth.toString()

            var mMonth = temp_Date.monthOfYear().getAsShortText(Locale.US)

            /*var mMonth1 = temp_Date.monthOfYear().getAsText(Locale.US)
            var mMonth2 = temp_Date.monthOfYear().getAsShortText(Locale.US)
            Log.i("MasterBlaster", " Experiment :-> ${mMonth1}")  // January,February...,December
            Log.i("MasterBlaster", " Experiment :-> ${mMonth2}")  // Jan,Feb,...,Dec

            <---- avoid this --->
            var mMonth = when(temp_Date.monthOfYear){
                DateTimeConstants.JANUARY-> "JAN"
                DateTimeConstants.FEBRUARY -> "FEB"
                DateTimeConstants.MARCH -> "MAR"
                DateTimeConstants.APRIL -> "APR"
                DateTimeConstants.MAY -> "MAY"
                DateTimeConstants.JUNE -> "JUN"
                DateTimeConstants.JULY -> "JUL"
                DateTimeConstants.AUGUST -> "AUG"
                DateTimeConstants.SEPTEMBER -> "SEP"
                DateTimeConstants.OCTOBER -> "OCT"
                DateTimeConstants.NOVEMBER -> "NOV"
                DateTimeConstants.DECEMBER -> "DEC"
                else -> "other_month" }*/

            var mDay = temp_Date.dayOfWeek().getAsText(Locale.US)
            Log.i("MasterBlaster", " mDay :-> ${mDay}")

            /*
            var mDay1 = temp_Date.dayOfWeek().getAsText(Locale.US)
            var mDay2 = temp_Date.dayOfWeek().getAsShortText(Locale.US)
            Log.i("MasterBlaster", " Experiment :-> ${mDay1}")  // Monday,Tuesday...,Sunday
            Log.i("MasterBlaster", " Experiment :-> ${mDay2}")  // Mon,Tue...,Sun

            <---- avoid this --->
            var mDay = when(temp_Date.dayOfWeek){
                DateTimeConstants.MONDAY -> "Monday"
                DateTimeConstants.TUESDAY -> "TUESDAY"
                DateTimeConstants.WEDNESDAY -> "WEDNESDAY"
                DateTimeConstants.THURSDAY -> "THURSDAY"
                DateTimeConstants.FRIDAY -> "FRIDAY"
                DateTimeConstants.SATURDAY -> "SATURDAY"
                DateTimeConstants.SUNDAY -> "SUNDAY"
                else -> "other_day" }*/

            if ((mDay == "Saturday") || (mDay == "Sunday")) {

                val row = Row(RowType.HOLIDAY, null, "Holiday", null)
                rowList.add(row)
            }

            var mTemp = (day.temperature + " Degree")


            val row = Row(mRowType, "", "", DayData(mDate, mMonth, mDay, mTemp))
            rowList.add(row)

            mPreviousYear = temp_Date.year()

        }
        Log.i("MasterBlaster", " Final RowType dataset :-> ${rowList}")

        return rowList
    }

    override fun onRowClicked(row: Row) {
        Log.i("MasterBlaster", " Row Clicked (MainAct) :-> ${row.rowType}")

        Toast.makeText(this, "Row Clicked : ${row.rowType}", Toast.LENGTH_SHORT).show()

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
