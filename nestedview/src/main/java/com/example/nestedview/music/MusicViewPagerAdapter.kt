package com.example.nestedview.music

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 */

class MusicViewPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    private val fragmentCount = 2

    override fun getItem(position: Int): Fragment {

        return when (position) {

            0 -> ValueBarFragment.getInstance()
            else -> ValueBarDetailsFragment.getInstance()
        }
    }

    override fun getCount(): Int = fragmentCount

}