package com.example.nestedview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.view.View
import com.example.nestedview.music.MusicTabPagerAdapter
import com.example.nestedview.music.MusicViewPagerAdapter
import kotlinx.android.synthetic.main.activity_collapsing_toolbar.*

class CollapsingToolbarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collapsing_toolbar)
        configureTopViewPager(supportFragmentManager)

        configureBottomViewPager(supportFragmentManager)
    }

    private fun configureTopViewPager(supportFragmentManager: FragmentManager) {

        var musicViewPagerAdapter = MusicViewPagerAdapter(supportFragmentManager)
        music_top_view_pager.adapter = musicViewPagerAdapter

        music_top_view_pager_indicator.setupWithViewPager(music_top_view_pager, true)
    }


    private fun configureBottomViewPager(supportFragmentManager: FragmentManager) {

        music_bottom_tab_layout.removeAllTabs()

        music_bottom_tab_layout.addTab(music_bottom_tab_layout.newTab().setText("Primary Music"))
        music_bottom_tab_layout.addTab(music_bottom_tab_layout.newTab().setText("Secondary Music"))

        music_bottom_tab_layout.tabGravity = TabLayout.GRAVITY_CENTER
        music_bottom_tab_layout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorAccent))
        music_seperator_line.visibility = View.VISIBLE

        music_bottom_view_pager.adapter = MusicTabPagerAdapter(supportFragmentManager, 2)

        //music_bottom_tab_layout.setupWithViewPager(music_bottom_view_pager,true)


        music_bottom_view_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(music_bottom_tab_layout))

        music_bottom_tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {

                music_bottom_view_pager.currentItem = tab?.position ?: 0
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }
}