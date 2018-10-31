package com.example.nestedview.favourite

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nestedview.R
import kotlinx.android.synthetic.main.favourite_card_item.view.*

/**
 */
class CardsPagerAdapter(context: Context, private val dataset: ArrayList<Card>?, private var favouriteCardItemClickListener: FavouriteCardItemClickListener) : PagerAdapter() {


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return dataset?.size ?: 0
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        // return super.instantiateItem(container, position)
        val layoutInflater = LayoutInflater.from(container.context)
        val view = layoutInflater.inflate(R.layout.favourite_card_item, container, false)
        val card = dataset?.get(position) ?: Card("", "")

        view.favourite_card_item_title.text = card.title
        view.favourite_card_item_message.text = card.message

        view.setOnClickListener { favouriteCardItemClickListener.onItemClick(card) }

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //super.destroyItem(container, position, `object`)
        container.removeView(`object` as View)
    }
}