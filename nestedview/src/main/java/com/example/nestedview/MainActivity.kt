package com.example.nestedview

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.toolbar_layout.*

// https://medium.com/@hitherejoe/exploring-the-android-design-support-library-bottom-navigation-drawer-548de699e8e0

class MainActivity : AppCompatActivity() {

    private var mCurrentItem = 0
    private var doubleBackToExitPressedOnce = false

    /* private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
         when (item.itemId) {

         }
         false
     }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        bottom_navigation.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {

                R.id.action_favourite -> {
                    toolbar.title = "Favourites"

                    // you can start new fragment here...
                    /*val favouriteFragment = FavouriteFragment.getInstance()
                    startFragment(favouriteFragment)*/

                    //But here we are setting the view pager fragment
                    main_view_pager.currentItem = 0
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.action_schedules -> {
                    toolbar.title = "Schedules"
                    // you can start new fragment here...

                    main_view_pager.currentItem = 1
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.action_music -> {
                    toolbar.title = "Music"
                    // you can start new fragment here...

                    main_view_pager.currentItem = 2
                    return@setOnNavigationItemSelectedListener true
                }

                else -> {
                    Log.i("MasterBlaster", "Wrong Selection")
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }


        var bottomNavigationPagerAdapter = BottomNavigationPagerAdapter(supportFragmentManager)
        main_view_pager.adapter = bottomNavigationPagerAdapter
        main_view_pager.offscreenPageLimit = 1  // By default it's one


        main_view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                Log.i("MasterBlaster", "Current selected item :- ${position}")
                mCurrentItem = position
            }
        })

    }

    private fun startFragment(fragment: Fragment) {

        supportFragmentManager
                .beginTransaction()
                //.replace(R.id.bottom_navigation_fragment_container,fragment)
                .replace(R.id.main_view_pager, fragment)
                .addToBackStack(null)
                .commit()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {

        //super.onBackPressed()
        if (mCurrentItem != 0) {

            //Setting the bottom navigation option manually
            /*
            bottom_navigation.menu.getItem(0).isChecked = true
            //Setting the view pager page manually
            main_view_pager.currentItem = 0
            */
            setBottomNavigationAndViewPager(0)

            Log.i("MasterBlaster", "Current selected item is not 0 ")

            return
        }

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please Click back once again to Exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)

    }

    // Common method to set the bottom navigation and respective view pager manually
    fun setBottomNavigationAndViewPager(id: Int) {

        when (id) {

            0 -> {
                //Setting the bottom navigation option manually
                bottom_navigation.menu.getItem(0).isChecked = true
                //Setting the view pager page manually
                main_view_pager.currentItem = 0
            }

            1 -> {
                //Setting the bottom navigation option manually
                bottom_navigation.menu.getItem(1).isChecked = true
                //Setting the view pager page manually
                main_view_pager.currentItem = 1
            }

            2 -> {
                //Setting the bottom navigation option manually
                bottom_navigation.menu.getItem(2).isChecked = true
                //Setting the view pager page manually
                main_view_pager.currentItem = 2
            }

            else -> {
                Log.i("MasterBlaster", "Wrong Selection")
            }

        }

    }
}
