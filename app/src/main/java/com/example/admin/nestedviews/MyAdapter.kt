package com.example.admin.nestedviews

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.content.view.*
import kotlinx.android.synthetic.main.header.view.*
import java.util.zip.Inflater

class MyAdapter(var context: Context, var dataSet: ArrayList<Row>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_CONTENT = 1
    }

    override fun getItemViewType(position: Int): Int {

        val type = when (dataSet[position].Type) {
            "HEADER" -> TYPE_HEADER
            else -> TYPE_CONTENT
        }
        Log.i("MasterBlaster", "type : ${type}")
        return type
        //return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (position) {

            TYPE_HEADER -> HeaderViewHolder(inflater.inflate(R.layout.header, parent, false))
            else -> ContentViewHolder(inflater.inflate(R.layout.content, parent, false))
        }
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewType = holder.itemViewType
        Log.i("onBindViewHolder", "viewType : ${viewType}")

        when (viewType) {

            TYPE_HEADER -> {
                (holder as HeaderViewHolder).headerBind(dataSet[position].HeaderText)
            }
            else -> {
                (holder as ContentViewHolder).contentBind(dataSet[position].ContentText)
            }
        }
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun headerBind(hText: String?) {
            Log.i("MasterBlaster", "hText : ${hText}")

            itemView.header.text = hText
        }
    }

    inner class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun contentBind(cText: String?) {
            Log.i("MasterBlaster", "cText : ${cText}")

            itemView.content.text = cText
        }
    }
}