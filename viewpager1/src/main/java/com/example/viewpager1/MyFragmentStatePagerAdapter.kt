package com.example.viewpager1

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 */
class MyFragmentStatePagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    private var fragmentCount = 3


    override fun getItem(position: Int): Fragment {

        return when (position) {

            0 -> FirstFragment.getInstance()
            1 -> SecondFragment.getInstance()
            else -> ThirdFragment.getInstance()
        }
    }

    override fun getCount(): Int {

        return fragmentCount
    }
}