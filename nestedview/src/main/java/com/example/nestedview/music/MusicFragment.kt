package com.example.nestedview.music


import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.nestedview.R
import kotlinx.android.synthetic.main.fragment_music.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MusicFragment : Fragment() {

    private lateinit var mContext: Context
    private lateinit var mFragmentActivityContext: FragmentActivity

    private lateinit var musicViewPagerAdapter: MusicViewPagerAdapter

    private var dotsCount = 0
    private lateinit var dots: Array<ImageView>

    companion object {
        // Method for creating new instances of the fragment
        fun getInstance(): MusicFragment {

            //Store the data into the Bundle object
            val bundle = Bundle()
            //bundle.putString(MovieHelper.KEY_TITLE, movie.title)
            //bundle.putInt(MovieHelper.KEY_RATING, movie.rating)

            //Create a new  fragment and set the bundle as an argument
            val musicFragment = MusicFragment()
            musicFragment.arguments = bundle
            return musicFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_music, container, false)

        val view = inflater.inflate(R.layout.fragment_music, container, false)


        // Retrieve and display the  data from bundle
        val bundle = arguments
        bundle?.let {

            //mTitleTextView.text = bundle.getString(MovieHelper.KEY_TITLE)
        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isAdded) {

            mContext = view.context
            Log.i("MasterBlaster", "MusicFragment, mContext :- ${mContext}")

            //var supportFragmentManager = (mContext as FragmentActivity).supportFragmentManager

            //mFragmentActivityContext = activity as FragmentActivity
            //var supportFragmentManager = mFragmentActivityContext.supportFragmentManager

            var supportFragmentManager = childFragmentManager

            //Log.i("MasterBlaster","MusicFragment, mFragmentActivityContext :- ${mFragmentActivityContext}")
            Log.i("MasterBlaster", "MusicFragment, supportFragmentManager :- ${supportFragmentManager}")


            configureTopViewPager(supportFragmentManager)

            configureBottomViewPager(supportFragmentManager)
        }
    }

    private fun configureTopViewPager(supportFragmentManager: FragmentManager) {

        musicViewPagerAdapter = MusicViewPagerAdapter(supportFragmentManager)
        music_top_view_pager.adapter = musicViewPagerAdapter

        music_top_view_pager_indicator.setupWithViewPager(music_top_view_pager, true)
        //configureDots()

        /*music_view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {

                // setting music_view_pager_indicator for Linear layout

                for(dotcount in 0..dotsCount){

                    dots[dotcount].setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.inactive_dot))
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.active_dot))*//**//*

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })*/

    }

    /*private fun configureDots() {

        dotsCount = musicViewPagerAdapter.count
        dots =  Array<ImageView>(dotsCount,  { x -> return ((ImageView(mContext)).setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.inactive_dot)))})
        //dots =  arrayOf<ImageView>()

        music_view_pager_indicator.removeAllViews()

        for (dotcount in 0..dotsCount){

             // dots[dotcount] = ImageView(mContext)
            //dots?.set(dotcount, ImageView(mContext))
            //dots?.get(dotcount)?.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.inactive_dot))
            //dots[dotcount].setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.inactive_dot))
            val params : LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(4,0,4,0)
            music_view_pager_indicator.addView(dots[dotcount])
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.active_dot))
    }*/


    private fun configureBottomViewPager(supportFragmentManager: FragmentManager) {

        music_bottom_tab_layout.removeAllTabs()

        music_bottom_tab_layout.addTab(music_bottom_tab_layout.newTab().setText("Primary Music"))
        music_bottom_tab_layout.addTab(music_bottom_tab_layout.newTab().setText("Secondary Music"))

        music_bottom_tab_layout.tabGravity = TabLayout.GRAVITY_CENTER
        music_bottom_tab_layout.setSelectedTabIndicatorColor(ContextCompat.getColor(mContext, R.color.colorAccent))
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

}// Required empty public constructor
