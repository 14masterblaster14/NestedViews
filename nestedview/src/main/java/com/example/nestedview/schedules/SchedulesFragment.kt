package com.example.nestedview.schedules


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nestedview.CollapsingToolbarActivity
import com.example.nestedview.R
import kotlinx.android.synthetic.main.fragment_schedules.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class SchedulesFragment : Fragment() {

    companion object {
        // Method for creating new instances of the fragment
        fun getInstance(): SchedulesFragment {

            //Store the data into the Bundle object
            val bundle = Bundle()
            //bundle.putString(MovieHelper.KEY_TITLE, movie.title)
            //bundle.putInt(MovieHelper.KEY_RATING, movie.rating)

            //Create a new Schedules fragment and set the bundle as an argument
            val schedulesFragment = SchedulesFragment()
            schedulesFragment.arguments = bundle
            return schedulesFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_schedules, container, false)
        val view = inflater.inflate(R.layout.fragment_schedules, container, false)


        // Retrieve and display the movie data from bundle
        val bundle = arguments
        bundle?.let {

            //mTitleTextView.text = bundle.getString(MovieHelper.KEY_TITLE)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.schedules_button.setOnClickListener { startActivity(Intent(view.context, CollapsingToolbarActivity::class.java)) }
    }

}// Required empty public constructor