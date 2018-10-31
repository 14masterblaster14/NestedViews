package com.example.stickyheaderswipelayout

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter
import kotlinx.android.synthetic.main.row_item.view.*
import kotlinx.android.synthetic.main.swipe_layout.view.*
import java.util.zip.Inflater

/**
 */
class SwipeAdapter(var context: Context, var dataset: ArrayList<Day>, var rowItemClickListener: SwipeItemRowClickListener) : RecyclerSwipeAdapter<RecyclerView.ViewHolder>() {

    var count: Int = 1

    override fun getSwipeLayoutResourceId(position: Int): Int {
        return R.id.swipeLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        return BaseViewHolder(layoutInflater.inflate(R.layout.swipe_layout, parent, false))

    }

    override fun getItemCount(): Int = dataset.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var baseViewHolder = holder as BaseViewHolder
        baseViewHolder.itemView.swipeLayout.showMode = SwipeLayout.ShowMode.PullOut

        if (dataset[position] != null) {
            baseViewHolder.bindData(dataset[position], rowItemClickListener)
        } else {

            baseViewHolder.itemView.swipeLayout.isRightSwipeEnabled = false
        }

    }


    inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindData(day: Day, rowItemClickListener: SwipeItemRowClickListener) {

            if (count % 2 == 0) {
                itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.gray))
            } else {
                itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorYellow))
            }
            count++

            itemView.txtView_Date.text = day.date
            itemView.txtView_temp.text = day.temperature

            var row: String = (" " + day.date + " has temp: " + day.temperature + " ")
            itemView.swipeLayout.surfaceView.setOnClickListener { _ -> rowItemClickListener.onRowItemClicked(row) }

            itemView.up_button.setOnClickListener {
                Log.i("MasterBlaster", "Up Button Clicked")
                Toast.makeText(context, "Up Button Clicked", Toast.LENGTH_SHORT).show()
            }

            itemView.down_button.setOnClickListener {
                Log.i("MasterBlaster", "Down Button Clicked")
                Toast.makeText(context, "Down Button Clicked", Toast.LENGTH_SHORT).show()
            }

            itemView.zero_button.setOnClickListener {
                Log.i("MasterBlaster", "Zero Button Clicked")
                Toast.makeText(context, "Zero Button Clicked", Toast.LENGTH_SHORT).show()
            }

        }


    }

}