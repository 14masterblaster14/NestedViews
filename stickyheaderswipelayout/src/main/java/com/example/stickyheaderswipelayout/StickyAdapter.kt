package com.example.stickyheaderswipelayout

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brandongogetap.stickyheaders.exposed.StickyHeaderHandler
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter
import kotlinx.android.synthetic.main.day.view.*
import kotlinx.android.synthetic.main.holiday.view.*
import kotlinx.android.synthetic.main.year.view.*

/**
 */
class StickyAdapter(private val context: Context, private var dataset: MutableList<Row>, private var rowClickListener: RowClickListener)
//: RecyclerView.StickyAdapter<RecyclerView.ViewHolder>() {
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(), StickyHeaderHandler {


    override fun getAdapterData(): MutableList<*> {
        return dataset
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {

            RowType.YEAR.ordinal -> {
                val yearViewHolder = holder as YearViewHolder
                yearViewHolder.bindYear(dataset[position], rowClickListener)
            }

            RowType.DAY.ordinal -> {
                val dayViewHolder = holder as DayViewHolder
                dayViewHolder.bindDay(dataset[position], rowClickListener)
            }

            RowType.HOLIDAY.ordinal -> {
                val holidayViewHolder = holder as HolidayViewHolder
                holidayViewHolder.bindHoliday(dataset[position], rowClickListener)
            }
        }
    }


    override fun getItemViewType(position: Int): Int {

        Log.i("MasterBlaster", "ordinal : ${dataset[position].rowType.ordinal} ")
        return dataset[position].rowType.ordinal

        // return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            RowType.YEAR.ordinal -> YearViewHolder(layoutInflater.inflate(R.layout.year, parent, false))
            RowType.DAY.ordinal -> DayViewHolder(layoutInflater.inflate(R.layout.day, parent, false))
            else -> HolidayViewHolder(layoutInflater.inflate(R.layout.holiday, parent, false))
        }
    }


    override fun getItemCount(): Int = dataset.size


    inner class YearViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindYear(row: Row, rowClickListener: RowClickListener) {

            itemView.year_title.text = row.year?.year

            itemView.setOnClickListener { _ -> rowClickListener.onRowClicked(row) }
        }

    }

    inner class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindDay(row: Row, rowClickListener: RowClickListener) {

            itemView.date.text = row.day?.date
            itemView.month.text = row.day?.month
            itemView.day.text = row.day?.day
            itemView.temperature.text = "Temperature"
            itemView.temperature_value.text = row.day?.temp


            itemView.setOnClickListener { _ -> rowClickListener.onRowClicked(row) }

        }
    }

    inner class HolidayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindHoliday(row: Row, rowClickListener: RowClickListener) {

            itemView.holiday_title.text = row.holiday

            itemView.setOnClickListener { _ -> rowClickListener.onRowClicked(row) }

        }
    }
}