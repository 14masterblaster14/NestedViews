package com.example.stickyheaderswipelayout

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.brandongogetap.stickyheaders.StickyLayoutManager
import com.brandongogetap.stickyheaders.exposed.StickyHeaderListener
import com.daimajia.swipe.util.Attributes
import kotlinx.android.synthetic.main.activity_swipe.*
import org.joda.time.LocalDate
import java.util.*

//https://github.com/daimajia/AndroidSwipeLayout


class SwipeActivity : AppCompatActivity(), SwipeItemRowClickListener {


    private var rowItemClickListener: SwipeItemRowClickListener = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe)

        val calenderDays: ArrayList<Day> = DBHelper.getDaysFromJson(this, "calender_days.json")

        Log.i("MasterBlaster", "dataSet :-> ${calenderDays}")

        swipeRecyclerView.layoutManager = LinearLayoutManager(this)
        var swipeAdapter = SwipeAdapter(this, calenderDays, rowItemClickListener)
        swipeAdapter.mode = Attributes.Mode.Single
        swipeRecyclerView.adapter = swipeAdapter
    }


    override fun onRowItemClicked(string: String) {

        Log.i("MasterBlaster", "Row Item Clicked -> ${string}")

        Toast.makeText(this, "Row Item Clicked -> ${string}", Toast.LENGTH_SHORT).show()

    }
}
