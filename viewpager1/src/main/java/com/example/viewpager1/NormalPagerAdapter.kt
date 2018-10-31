package com.example.viewpager1

import android.app.FragmentManager
import android.support.v4.view.PagerAdapter
import android.view.View

/**
 */
class NormalPagerAdapter : PagerAdapter() {

    var fragmentCount = 3

    override fun isViewFromObject(view: View, `object`: Any): Boolean {

        return true
    }

    override fun getCount(): Int {

        return fragmentCount

    }
}