package com.example.apirecyclerviewtimber

import android.support.v7.widget.RecyclerView

/**
 */
abstract class BaseAdapter<Type : RecyclerView.ViewHolder, Data> : RecyclerView.Adapter<Type>() {

    abstract fun setData(data: List<Data>)
}