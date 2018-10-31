package com.example.apirecyclerviewtimber

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_movie_list.view.*

/**
 */
class MoviesAdapter(private var movieEntities: List<Movie>, private var movieListCallback: MovieListCallback)
    : BaseAdapter<MoviesAdapter.MovieViewHolder, Movie>() {

    /*private lateinit var movieEntities : List<Movie>

    constructor(  movieEntities1 : List<Movie>, movieListCallback: MovieListCallback): this(movieListCallback){
        movieEntities = movieEntities1
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesAdapter.MovieViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context) //.inflate(R.layout.item_movie_list,parent,false)
        val view = layoutInflater.inflate(R.layout.item_movie_list, parent, false)
        return MovieViewHolder(view)
        /*var mDataBinding : ItemMovieListBinding = DataBindingUtil.inflate<ItemMovieListBinding>(LayoutInflater.from(parent.context),R.layout.item_movie_list,parent,false)
        return MovieViewHolder(mDataBinding)*/


        /*val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_list,parent,false)
        return MovieViewHolder(view)*/
    }

    override fun onBindViewHolder(movieViewHolder: MoviesAdapter.MovieViewHolder, position: Int) {

        movieViewHolder.bindMovie(movieEntities[position], movieListCallback)

    }

    override fun getItemCount(): Int = movieEntities.size


    override fun setData(data: List<Movie>) {
        movieEntities = data
        notifyDataSetChanged()
    }

    /* inner class MovieViewHolder (private var mDataBinding : ItemMovieListBinding) : RecyclerView.ViewHolder(mDataBinding.root) {

         fun bindMovie(movieEntity: Movie, movieListCallback: MovieListCallback) {

             //mDataBinding.movie = movieEntity
             mDataBinding.title.text =  movieEntity.title
             mDataBinding.subtitle.text = movieEntity.originalLanguage
             mDataBinding.description.text = movieEntity.overview
             mDataBinding.ratingText.text = movieEntity.voteAverage.toString()

             if(movieListCallback != null){

                 mDataBinding.root.setOnClickListener {_ -> movieListCallback.onMovieClicked(movieEntity)}
                 //mDataBinding.root.setOnClickListener({ _ -> movieListCallback.onMovieClicked(mDataBinding.movie,mDataBinding.imageViewCover)})
             }
             mDataBinding.executePendingBindings()
         }
     }*/

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindMovie(movieEntity: Movie, movieListCallback: MovieListCallback) {

            val title = itemView.title as TextView
            val subtitle = itemView.subtitle as TextView
            val description = itemView.description as TextView
            val rating = itemView.rating_text as TextView

            title.text = movieEntity.title
            subtitle.text = movieEntity.originalLanguage
            description.text = movieEntity.overview
            rating.text = movieEntity.voteAverage.toString()

            if (movieListCallback != null) {

                itemView.setOnClickListener { _ -> movieListCallback.onMovieClicked(movieEntity) }
            }

        }
    }

}
