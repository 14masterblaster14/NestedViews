package com.example.viewpager

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

//https://www.raywenderlich.com/169774/viewpager-tutorial-android-getting-started-kotlin


class MainActivity : AppCompatActivity() {

    private lateinit var mViewPager: ViewPager
    private lateinit var mPagerAdapter: MoviesPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        mViewPager = viewPager
        // Get the list of movies from the JSON file
        val movies = MovieHelper.getMoviesFromJson(this, "movies.json")

        mPagerAdapter = MoviesPagerAdapter(supportFragmentManager, movies)
        mViewPager.adapter = mPagerAdapter

        //If u you want to set the Endless scrolling
        //To ensure that the movie displayed at the beginning will still be the first one in your list,
        // set MAX_VALUE to be an even number (in this case 200 works fine)
        //This way, after you divide pagerAdapter.count by 2,
        //pagerAdapter.count % movies.size = 0 (which is the first index that the ViewPager asks for when the app starts).
        //The Endless scrolling wont work with tablayout you need to implement with the help of "com.nshmura:recyclertablayout:1.5.0"

        //viewPager.currentItem = mPagerAdapter.count / 2
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
}
