package com.example.nestedview.favourite

import android.app.Activity
import android.app.FragmentManager
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.nestedview.R
import kotlinx.android.synthetic.main.favourite_card.view.*
import kotlinx.android.synthetic.main.favourite_list.view.*
import kotlinx.android.synthetic.main.favourite_progress.view.*
import java.util.*

/**
 */
/*class FavouriteFragmentAdapter(private var dataset: ArrayList<FavouriteMainRow>, private var favouriteMainRowClickListener: FavouriteMainRowClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {*/
class FavouriteFragmentAdapter(private val context: Context, private var dataset: ArrayList<FavouriteMainRow>, private var favouriteMainRowClickListener: FavouriteMainRowClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun getItemViewType(position: Int): Int {

        Log.i("MasterBlaster", "getItemViewType ordinal : ${dataset[position].rowType.ordinal} ")
        return dataset[position].rowType.ordinal
        //return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {

            RowType.PROGRESS_VIEW.ordinal -> ProgressViewHolder(layoutInflater.inflate(R.layout.favourite_progress, parent, false))

            RowType.LIST_VIEW.ordinal -> ListViewHolder(layoutInflater.inflate(R.layout.favourite_list, parent, false))

            else -> CardsViewHolder(layoutInflater.inflate(R.layout.favourite_card, parent, false))
        }

    }

    override fun getItemCount(): Int = dataset.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {

            RowType.PROGRESS_VIEW.ordinal -> {
                val holder = holder as ProgressViewHolder
                holder.bindProgressData(dataset[position], favouriteMainRowClickListener)
            }

            RowType.LIST_VIEW.ordinal -> {
                val holder = holder as ListViewHolder
                holder.bindListData(dataset[position], favouriteMainRowClickListener)
            }

            else -> {
                val holder = holder as CardsViewHolder
                holder.bindCardsData(dataset[position], favouriteMainRowClickListener)
            }

        }
    }


    inner class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindProgressData(favouriteMainRow: FavouriteMainRow, favouriteMainRowClickListener: FavouriteMainRowClickListener) {

            itemView.setOnClickListener { favouriteMainRowClickListener.onRowClicked(favouriteMainRow) }

            itemView.favourite_progress_title.text = "Showing Progress"
            itemView.favourite_progress_view_pager.adapter = ProgressPagerAdapter(context, favouriteMainRow.mProgress, 0.3f, object : FavouriteProgressItemClickListener {
                override fun onItemClick(season: Season) {

                    Toast.makeText(context, "Season Clicked :-> ${season}", Toast.LENGTH_SHORT).show()
                }
            })

        }
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindListData(favouriteMainRow: FavouriteMainRow, favouriteMainRowClickListener: FavouriteMainRowClickListener) {

            itemView.setOnClickListener { favouriteMainRowClickListener.onRowClicked(favouriteMainRow) }

            itemView.favourite_list_title.text = "Showing Months"
            itemView.favourite_list_recyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            itemView.favourite_list_recyclerview.adapter = FavouriteListAdapter(context, favouriteMainRow.mList, object : FavouriteListItemClickListener {

                override fun onItemClick(monthString: String) {
                    Toast.makeText(context, "Month Clicked :-> ${monthString}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }


    inner class CardsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindCardsData(favouriteMainRow: FavouriteMainRow, favouriteMainRowClickListener: FavouriteMainRowClickListener) {

            itemView.favourite_card_title.text = "Showing Cards"
            itemView.favourite_card_viewpager.adapter = CardsPagerAdapter(context, favouriteMainRow.mCard, object : FavouriteCardItemClickListener {
                override fun onItemClick(card: Card) {

                    Toast.makeText(context, "Card Clicked :-> ${card.title}", Toast.LENGTH_SHORT).show()
                }
            })

            itemView.setOnClickListener { favouriteMainRowClickListener.onRowClicked(favouriteMainRow) }
        }
    }

}