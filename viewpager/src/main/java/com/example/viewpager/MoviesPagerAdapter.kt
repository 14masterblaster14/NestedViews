package com.example.viewpager

import android.support.v4.app.FragmentManager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter

/**
 */
class MoviesPagerAdapter(fragmentManager: FragmentManager, private val movies: ArrayList<Movie>) : FragmentStatePagerAdapter(fragmentManager) {

    //If u you want to set the Endless scrolling
    companion object {
        private const val MAX_VALUE = 200

    }


    override fun getItem(position: Int): Fragment {
        return MovieFragment.getInstance(movies[position])

        //If u you want to set the Endless scrolling
        //return MovieFragment.getInstance(movies[position % movies.size])

    }

    override fun getCount(): Int {
        return movies.size

        //If u you want to set the Endless scrolling
        //return movies.size * MAX_VALUE

    }
}