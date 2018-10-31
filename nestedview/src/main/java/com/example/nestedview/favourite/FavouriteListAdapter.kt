package com.example.nestedview.favourite

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nestedview.R
import kotlinx.android.synthetic.main.favourite_list_item.view.*

/**
 */
class FavouriteListAdapter(private val context: Context, private var dataset: ArrayList<String>?, private var favouriteListItemClickListener: FavouriteListItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mColorCount = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.favourite_list_item, parent, false)
        return mViewHolder(view)

    }

    override fun getItemCount(): Int = dataset?.size ?: 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var holder = holder as mViewHolder
        holder.bindData(dataset?.get(position) ?: "", favouriteListItemClickListener)
        mColorCount += 1
    }

    inner class mViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(month: String, favouriteListItemClickListener: FavouriteListItemClickListener) {

            itemView.favourite_list_item_month.text = month

            if (mColorCount % 2 == 0) {

                itemView.setBackgroundColor(Color.LTGRAY)

            } else {

                itemView.setBackgroundColor(Color.CYAN)
            }

            itemView.setOnClickListener { favouriteListItemClickListener.onItemClick(month) }

        }
    }


}