package com.example.nestedview.favourite

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.nestedview.R
import kotlinx.android.synthetic.main.custom_progress_bar.view.*

/**
 */
class ProgressPagerAdapter(private val context: Context, private var progressList: ArrayList<Season>?
                           , private var pageWidth: Float, var favouriteProgressItemClickListener: FavouriteProgressItemClickListener)
    : PagerAdapter() {


    override fun isViewFromObject(view: View, `object`: Any): Boolean {

        return view == `object`
    }

    override fun getCount(): Int {

        return progressList?.size ?: 0
    }

    // Overriding Methods

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        //return super.instantiateItem(container, position)

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.custom_progress_bar, null)
        val viewHolder = ViewHolder(view)

        val season: Season = progressList?.get(position) ?: Season(true, "", "", 0, 0f, 0f)
        viewHolder.bindData(season)
        view.setOnClickListener { favouriteProgressItemClickListener.onItemClick(season) }

        view.tag = viewHolder
        container.addView(view, 0)

        //var progressBar = view.custom_progress_bar as CustomProgressBar
        //progressBar.setUpProgressBar(season.isAnimated,season.title,season.symbol,season.percent,season.startPoint,season.endPoint)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //super.destroyItem(container, position, `object`)
        container.removeView(`object` as View)
    }

    override fun getPageWidth(position: Int): Float {
        //return super.getPageWidth(position)
        return pageWidth
    }

    inner class ViewHolder(itemview: View) {

        private var progressBar: CustomProgressBar = itemview.custom_progress_bar

        fun bindData(season: Season) {

            progressBar.setUpProgressBar(season.isAnimated, season.title, season.symbol, season.percent, season.startPoint, season.endPoint)

        }

    }
}