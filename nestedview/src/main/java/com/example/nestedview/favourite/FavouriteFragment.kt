package com.example.nestedview.favourite


import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.nestedview.MainActivity
import com.example.nestedview.R
import kotlinx.android.synthetic.main.fragment_favourite.*
import kotlinx.android.synthetic.main.fragment_favourite.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FavouriteFragment : Fragment(), FavouriteMainRowClickListener {

    private lateinit var mainActivity: MainActivity
    private var favouriteMainRowClickListener = this
    private var mHandler = Handler()

    companion object {

        // Method for creating new instances of the fragment
        fun getInstance(): FavouriteFragment {

            //Store the data into the Bundle object
            val bundle = Bundle()
            //bundle.putString(MovieHelper.KEY_TITLE, movie.title)
            //bundle.putInt(MovieHelper.KEY_RATING, movie.rating)

            //Create a new Favourite fragment and set the bundle as an argument
            val favouriteFragment = FavouriteFragment()
            favouriteFragment.arguments = bundle
            return favouriteFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_favourite, container, false)

        val view = inflater.inflate(R.layout.fragment_favourite, container, false)
        // Retrieve and display the  data from bundle
        val bundle = arguments
        bundle?.let {

            //mTitleTextView.text = bundle.getString(MovieHelper.KEY_TITLE)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as MainActivity

        prepareAndLoadData(view)

        favourite_swipetorefresh.setOnRefreshListener {

            favourite_swipetorefresh.isRefreshing = true
            favourite_main_recyclerview.visibility = View.INVISIBLE

            mHandler.postDelayed({ run { prepareAndLoadData(view) } }, 4000)

        }
    }

    fun prepareAndLoadData(view: View) {

        // Handling the views


        favourite_swipetorefresh.isRefreshing = false
        favourite_main_recyclerview.visibility = View.VISIBLE

        //Creating List<Season> for progress view
        var progressList = ArrayList<Season>()
        progressList.add(Season(true, "Summer", "%", 25, 0f, 90f))
        progressList.add(Season(true, "Rainy", "%", 25, 90f, 90f))
        progressList.add(Season(true, "Winter", "%", 25, 180f, 90f))
        progressList.add(Season(true, "Fall", "%", 25, 270f, 90f))
        Log.i("MasterBlaster", "progressList :- ${progressList}")


        //Creating ArrayList<String> for List view
        var listForList = ArrayList<String>()
        listForList.add("JANUARY")
        listForList.add("JULY")
        listForList.add("OCTOBER")
        listForList.add("DECEMBER")
        Log.i("MasterBlaster", "listForList :- ${listForList}")


        //Creating ArrayList<String> for Card view
        var listForCard = ArrayList<Card>()
        listForCard.add(Card("One Plus", "Never Settle"))
        listForCard.add(Card("Nokia", "Connecting People"))
        listForCard.add(Card("Motorola", "by Microsoft"))
        listForCard.add(Card("Pixel", "by Google"))
        Log.i("MasterBlaster", "listForCard :- ${listForCard}")


        //loading the favourite fragment
        var dataset = ArrayList<FavouriteMainRow>()
        dataset.add(FavouriteMainRow(RowType.PROGRESS_VIEW, progressList, null, null))
        dataset.add(FavouriteMainRow(RowType.LIST_VIEW, null, listForList, null))
        dataset.add(FavouriteMainRow(RowType.CARD_VIEW, null, null, listForCard))

        view.favourite_main_recyclerview.addItemDecoration(DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL))
        view.favourite_main_recyclerview.layoutManager = LinearLayoutManager(mainActivity)
        view.favourite_main_recyclerview.adapter = FavouriteFragmentAdapter(mainActivity, dataset, favouriteMainRowClickListener)
        view.favourite_main_recyclerview.hasFixedSize()
    }

    // Main Recycler view Row i.e. Progres / List / Card
    override fun onRowClicked(favouriteMainRow: FavouriteMainRow) {

        Toast.makeText(mainActivity, "Row Type Clicked : -> ${favouriteMainRow.rowType}", Toast.LENGTH_SHORT).show()
    }

}
