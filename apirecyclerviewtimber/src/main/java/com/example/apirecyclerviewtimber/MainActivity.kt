package com.example.apirecyclerviewtimber

import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity(), MovieListCallback {

    private var movieListCallback: MovieListCallback = this
    private var movieList1: List<Movie>? = null
    //private lateinit var mDataBinding : ActivityMainBinding
    private lateinit var mRecyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        /*
      //Timber
      */

        if (BuildConfig.DEBUG) {
            //Default Debug Tree
            //Timber.plant(Timber.DebugTree())
            //Custom Debug Tree
            Timber.plant(CustomDebugTree())

        } else {
            Timber.plant(ReleaseTree())
            //Timber.plant(CrashReportingTree())
        }

        // Currency localization and Spannable string
        enjoy()


        //setContentView(R.layout.activity_main)
        //mDataBinding  = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val formattedAmount = getFormattedAmountWithKr(this, "12345.3567")
        Log.i("MasterBlaster", "Formatted Amount is ${formattedAmount}")
        Timber.i("Formatted Amount is ${formattedAmount}")
        Timber.v("Formatted Amount is ${formattedAmount}")
        Timber.w("Formatted Amount is ${formattedAmount}")
        Timber.d("Formatted Amount is ${formattedAmount}")
        Timber.e("Formatted Amount is ${formattedAmount}")
        Timber.wtf("Formatted Amount is ${formattedAmount}")
        Timber.tag("MasterBlaster").i("This is custom tag")

        txt_view.text = formattedAmount


        mRecyclerView = recycler_view
        //mRecyclerView.layoutManager = GridLayoutManager(this,2)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.hasFixedSize()

        /*//<1>  Static Data
        addMovies()
        mRecyclerView.adapter = MoviesAdapter(movieList1!!, this)*/


        //<2>  Dynamic Data

        ApiClient.getApiInterface().loadMovies().enqueue(object : Callback<MoviesResponse> {

            override fun onResponse(call: Call<MoviesResponse>?, response: Response<MoviesResponse>?) {
                response?.let { response1 ->
                    if (response1.isSuccessful) {
                        val body = response1.body()
                        body?.let {

                            val movieList = body.results!!
                            Log.d("MasterBlaster", "Movies Size Received ${movieList.size}")
                            Log.d("MasterBlaster", "Movies Received $movieList")
                            mRecyclerView.adapter = MoviesAdapter(movieList, movieListCallback)

                        }

                    } else {
                        Log.d("MasterBlaster", "Received Failed response")
                    }
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable?) {
                Log.e("MasterBlaster", "Error in loading movies")
            }
        })


        /*//<3> Live Data

        val movieList2 : LiveData<List<Movie>> = Repository.getMovies()
        Log.d("MasterBlasterLiveData","Movies Received ${movieList2.value}")
        //mRecyclerView.adapter = MoviesAdapter(movieList2, this)*/

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

    }


    override fun onMovieClicked(movieEntity: Movie) {

        Toast.makeText(this, "Movie Clicked :  ${movieEntity.title}", Toast.LENGTH_SHORT).show()

    }

    private fun addMovies() {

        var movie1 = Movie()
        movie1.id = 1
        movie1.posterPath = "abc"
        movie1.adult = true
        movie1.overview = "bcd"
        movie1.originalTitle = "cde"
        movie1.title = "def"
        movie1.voteCount = 10
        movie1.voteAverage = 70.0
        movie1.backdropPath = "ef"
        movie1.originalLanguage = "fgh"

        var movie2 = Movie()
        movie2.id = 2
        movie2.posterPath = "a"
        movie2.adult = true
        movie2.overview = "ccd"
        movie2.originalTitle = "c"
        movie2.title = "xyz"
        movie2.voteCount = 20
        movie2.voteAverage = 80.0
        movie2.backdropPath = "fg"
        movie2.originalLanguage = "xyz"

        var movie3 = Movie()
        movie3.id = 3
        movie3.posterPath = "abc"
        movie3.adult = true
        movie3.overview = "ccde"
        movie3.originalTitle = "cde"
        movie3.title = "pqr"
        movie3.voteCount = 30
        movie3.voteAverage = 90.00
        movie3.backdropPath = "efg"
        movie3.originalLanguage = "pqr"


        var movie4 = Movie()
        movie4.id = 4
        movie4.posterPath = "abc"
        movie4.adult = true
        movie4.overview = "ccdb"
        movie4.originalTitle = "cde"
        movie4.title = "lmn"
        movie4.voteCount = 40
        movie4.voteAverage = 100.0
        movie4.backdropPath = "eg"
        movie4.originalLanguage = "lmn"

        movieList1 = listOf(movie1, movie2, movie3, movie4)
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


    /*fun getMovies(){

       ApiClient.getApiInterface().loadMovies().enqueue(object : Callback<MoviesResponse> {

           override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
               response.let { response1 ->
                   if(response1.isSuccessful){
                       val body = response1.body()
                       body.let {

                           if(body == null){
                               Log.d("MasterBlaster","Received Null body response")

                           }else{
                               movieList = body.results
                               Log.d("MasterBlaster","Movies Size Received ${movieList?.size}")
                               Log.d("MasterBlaster","Movies Received $movieList")
                           }
                       }

                   }else{
                       Log.d("MasterBlaster","Received Failed response")
                   }
               }
           }

           override fun onFailure(call: Call<MoviesResponse>, t: Throwable?) {
               Log.e("MasterBlaster","Error in loading movies")
           }
       } )
   }*/

    /*
        // Number Formatting and String Manipulations
    */

    val mLocale = Locale("nb")


    fun enjoy() {

        var number = 1234567.85555
        var string1 = number.toString()
        Log.i("MasterBlaster", "Formatted String Value-->" + string1)

        var dString = string1.toDouble()
        Log.i("MasterBlaster", "Formatted Double Value-->" + dString)

        val currencyFormatter = NumberFormat.getInstance(mLocale)
        val formattedStrring: String
        currencyFormatter.maximumFractionDigits = 2
        currencyFormatter.roundingMode = RoundingMode.HALF_UP
        formattedStrring = currencyFormatter.format(dString)
        Log.i("MasterBlaster", "Formatted Currency Value-->" + formattedStrring)

    }


    fun getFormattedAmountWithKr(context: Context, amount: String): SpannableString {
        if (TextUtils.isEmpty(amount)) {
            return SpannableString("")
        }
        val totalSumSpannable = SpannableString("Kr  " + getFormattedAmount(amount, false))
        totalSumSpannable.setSpan(RelativeSizeSpan(1.60f), 0, 2, 0) // set size
        totalSumSpannable.setSpan(StyleSpan(Typeface.NORMAL), 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE) // set bold
        totalSumSpannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.primary_dark_material_dark)), 0, 2, 0)// set color
        return totalSumSpannable
    }


    fun getFormattedAmount(amount: String, roundDecimal: Boolean): String {

        return getFormattedAmount(mLocale, java.lang.Double.valueOf(amount), roundDecimal, 2)

    }


    fun getFormattedAmount(locale: Locale, amount: Double, roundDecimal: Boolean, decimalPoints: Int): String {
        val currencyFormatter = NumberFormat.getInstance(locale)
        val formattedAmount: String
        if (roundDecimal) {
            //currencyFormatter.maximumFractionDigits = 0
            currencyFormatter.maximumFractionDigits = 2
            formattedAmount = currencyFormatter.format(Math.round(amount))
        } else {
            currencyFormatter.roundingMode = RoundingMode.HALF_UP
            currencyFormatter.maximumFractionDigits = decimalPoints
            formattedAmount = currencyFormatter.format(amount)
        }

        Log.i("MasterBlaster", "Formatted Value-->" + formattedAmount)
        return formattedAmount
    }

}
