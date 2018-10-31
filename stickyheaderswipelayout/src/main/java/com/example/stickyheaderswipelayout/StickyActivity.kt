package com.example.stickyheaderswipelayout

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.brandongogetap.stickyheaders.StickyLayoutManager
import com.brandongogetap.stickyheaders.exposed.StickyHeaderListener
import kotlinx.android.synthetic.main.activity_sticky.*
import org.joda.time.LocalDate
import java.util.*

class StickyActivity : AppCompatActivity(), RowClickListener {

    private var rowClickListener: RowClickListener = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sticky)


        titleView.text = "temperature"

        titleView.setOnClickListener {}

        val calenderDays: ArrayList<Day> = DBHelper.getDaysFromJson(this, "calender_days.json")

        Log.i("MasterBlaster", "dataSet :-> ${calenderDays}")

        val mAdapter = StickyAdapter(this, getDataset(calenderDays), rowClickListener)
        recyclerView.adapter = mAdapter

        var stickyLayoutManager = StickyLayoutManager(this, mAdapter)
        stickyLayoutManager.orientation = StickyLayoutManager.VERTICAL
        stickyLayoutManager.isAutoMeasureEnabled = true

        stickyLayoutManager.setStickyHeaderListener(object : StickyHeaderListener {

            override fun headerAttached(headerView: View, adapterPosition: Int) {
                val params = headerView.layoutParams as FrameLayout.LayoutParams
                params.topMargin = 0
                headerView.layoutParams = params
                headerView.findViewById<View>(R.id.year_line).visibility = View.INVISIBLE
            }

            override fun headerDetached(headerView: View, adapterPosition: Int) {
                //Empty comment to avoid sonar issue
            }
        })

        stickyLayoutManager.elevateHeaders(true)
        recyclerView.layoutManager = stickyLayoutManager

        //recyclerView.layoutManager = LinearLayoutManager(this)

        mAdapter.notifyDataSetChanged()
        //recyclerView.hasFixedSize()
    }

    private fun getDataset(calenderDays: ArrayList<Day>): MutableList<Row> {

        var rowList = ArrayList<Row>()
        var isFirstElement = true
        var mPreviousYear: LocalDate.Property? = null

        for (day in calenderDays) {

            var temp_Date: LocalDate = LocalDate.parse(day.date)
            Log.i("MasterBlaster", " Converted date :-> ${temp_Date}")


            if ((isFirstElement) || (mPreviousYear != temp_Date.year())) {
                val row = Row(RowType.YEAR, HeaderYear(temp_Date.year.toString()), null, null)
                rowList.add(row)

                isFirstElement = false
            }

            var mRowType = RowType.DAY

            var mDate = temp_Date.dayOfMonth.toString()

            var mMonth = temp_Date.monthOfYear().getAsShortText(Locale.US)

            var mDay = temp_Date.dayOfWeek().getAsText(Locale.US)
            Log.i("MasterBlaster", " mDay :-> ${mDay}")

            if ((mDay == "Saturday") || (mDay == "Sunday")) {
                val row = Row(RowType.HOLIDAY, null, "Holiday", null)
                rowList.add(row)
            }

            var mTemp = (day.temperature + " Degree")

            val row = Row(mRowType, null, "", DayData(mDate, mMonth, mDay, mTemp))
            rowList.add(row)

            mPreviousYear = temp_Date.year()

        }
        Log.i("MasterBlaster", " Final RowType dataset :-> ${rowList}")

        var rowList1: MutableList<Row> = rowList
        return rowList1
    }

    override fun onRowClicked(row: Row) {

        Log.i("MasterBlaster", " Row Clicked (MainAct) :-> ${row.rowType}")

        Toast.makeText(this, "Row Clicked : ${row.rowType}", Toast.LENGTH_SHORT).show()


    }
}
