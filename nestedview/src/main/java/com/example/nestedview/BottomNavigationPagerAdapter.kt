package com.example.nestedview

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.nestedview.favourite.FavouriteFragment
import com.example.nestedview.music.MusicFragment
import com.example.nestedview.schedules.SchedulesFragment

/**
 *
 */
class BottomNavigationPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    private val fragmentCount = 3

    override fun getItem(position: Int): Fragment {

        return when (position) {

            0 -> FavouriteFragment.getInstance()
            1 -> SchedulesFragment.getInstance()
            else -> MusicFragment.getInstance()  // for 3
        }
    }


    override fun getCount(): Int {

        return fragmentCount
    }
}