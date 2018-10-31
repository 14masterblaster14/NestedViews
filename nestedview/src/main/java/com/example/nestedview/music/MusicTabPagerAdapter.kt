package com.example.nestedview.music

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 */

// We can use this when we will use Tab Layout with View Pager.

class MusicTabPagerAdapter(private var fragmentManager: FragmentManager, private var tabCount: Int) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {

        return when (position) {

            0 -> MusicPrimaryFragment.getInstance()
            else -> MusicSecondaryFragment.getInstance()
        }

    }

    override fun getCount(): Int {
        return tabCount
    }
}