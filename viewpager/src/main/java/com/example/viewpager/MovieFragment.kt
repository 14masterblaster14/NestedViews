package com.example.viewpager


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MovieFragment : Fragment() {

    companion object {
        // Method for creating new instances of the fragment
        fun getInstance(movie: Movie): MovieFragment {

            //Store the movie data into the Bundle object
            val bundle = Bundle()
            bundle.putString(MovieHelper.KEY_TITLE, movie.title)
            bundle.putInt(MovieHelper.KEY_RATING, movie.rating)
            bundle.putString(MovieHelper.KEY_POSTER_URI, movie.posterUri)
            bundle.putString(MovieHelper.KEY_OVERVIEW, movie.overview)

            //Create a new Movie fragment and set the bundle as an argument
            val movieFragment = MovieFragment()
            movieFragment.arguments = bundle
            return movieFragment
        }

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_movie, container, false)

        val view = inflater.inflate(R.layout.fragment_movie, container, false)

        var mTitleTextView = view.findViewById<TextView>(R.id.titleTextView)
        var mRatingTextView = view.findViewById<TextView>(R.id.ratingTextView)
        var mOverviewTextView = view.findViewById<TextView>(R.id.overviewTextView)
        var mPosterImageView = view.findViewById<ImageView>(R.id.posterImageView)

        // Retrieve and display the movie data from bundle
        val bundle = arguments
        bundle?.let {

            mTitleTextView.text = bundle.getString(MovieHelper.KEY_TITLE)
            mRatingTextView.text = String.format("%d/10", bundle.getInt(MovieHelper.KEY_RATING))
            mOverviewTextView.text = bundle.getString(MovieHelper.KEY_OVERVIEW)

            //Download the image and display it using Picasso
            var imageId = resources.getIdentifier(bundle.getString(MovieHelper.KEY_POSTER_URI), "drawable", activity?.packageName)

            /*Picasso.with(activity)
                    .load(imageId)
                    .into(mPosterImageView)
*/
            //display it using Glide
            activity?.let {
                Glide.with(activity!!)
                        .load(imageId)
                        .into(mPosterImageView)
            }

        }

        return view
    }

}
