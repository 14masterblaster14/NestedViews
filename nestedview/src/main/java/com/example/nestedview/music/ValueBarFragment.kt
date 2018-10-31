package com.example.nestedview.music


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nestedview.R
import kotlinx.android.synthetic.main.fragment_value_bar.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ValueBarFragment : Fragment() {

    companion object {
        // Method for creating new instances of the fragment
        fun getInstance(): ValueBarFragment {

            //Store the data into the Bundle object
            val bundle = Bundle()
            //bundle.putString(MovieHelper.KEY_TITLE, movie.title)
            //bundle.putInt(MovieHelper.KEY_RATING, movie.rating)

            //Create a new fragment and set the bundle as an argument
            val valueBarFragment = ValueBarFragment()
            valueBarFragment.arguments = bundle
            return valueBarFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_value_bar, container, false)
        val view = inflater.inflate(R.layout.fragment_value_bar, container, false)


        // Retrieve and display the data from bundle
        val bundle = arguments
        bundle?.let {

            //mTitleTextView.text = bundle.getString(MovieHelper.KEY_TITLE)
        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.music_value_bar_title.text = "Showing Updation Progress..."
        view.valueBar.setMaxValue(100)
        view.valueBar.setAnimation(true)
        view.valueBar.setAnimationDuration(7000L)
        view.valueBar.setValue(70)
    }


}// Required empty public constructor
