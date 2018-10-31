package com.example.viewpager1


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * A simple [Fragment] subclass.
 */
class FirstFragment : Fragment() {


    companion object {
        // Method for creating new instances of the fragment
        fun getInstance(): FirstFragment {

            //Store the data into the Bundle object
            val bundle = Bundle()
            //bundle.putString(MovieHelper.KEY_TITLE, movie.title)
            //bundle.putInt(MovieHelper.KEY_RATING, movie.rating)

            val firstFragment = FirstFragment()
            firstFragment.arguments = bundle
            return firstFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_first, container, false)
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        // Retrieve and display the  data from bundle
        val bundle = arguments
        bundle?.let {

            //mTitleTextView.text = bundle.getString(MovieHelper.KEY_TITLE)
        }

        return view
    }

}// Required empty public constructor
