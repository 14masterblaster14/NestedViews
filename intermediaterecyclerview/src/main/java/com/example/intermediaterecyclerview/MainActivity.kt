package com.example.intermediaterecyclerview

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.intermediaterecyclerview.models.Photo
import com.example.intermediaterecyclerview.models.PhotoList
import com.example.intermediaterecyclerview.models.PhotoRow
import com.example.intermediaterecyclerview.models.RowType
import com.example.intermediaterecyclerview.service.NasaPhotos

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// API : https://api.nasa.gov/index.html#live_example
// Key : <your api key>
// example : https://api.nasa.gov/planetary/apod?api_key=<your api key>
// https://www.raywenderlich.com/172711/intermediate-recyclerview

//private const val TAG = "MarsRover"

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MarsRover"
    }

    private var currentRover = "curiosity"
    private var currentRoverPosition = 0
    private var currentCameraPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        setupSpinners()
        loadPhotos()

        recycler_view.visibility = View.GONE
        recycler_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recycler_view.layoutManager = LinearLayoutManager(this)

    }


    private fun setupSpinners() {
        setupRoverSpinners()
        setupCameraSpinners()
    }

    private fun setupRoverSpinners() {

        // Setup the spinners for selecting different rovers and cameras
        val roverStrings = resources.getStringArray(R.array.rovers)
        val adapter = ArrayAdapter.createFromResource(this, R.array.rovers, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        rovers.adapter = adapter

        rovers.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>) {
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (currentRoverPosition != position) {
                    currentRover = roverStrings[position].toLowerCase()
                    loadPhotos()
                }
                currentRoverPosition = position
            }
        }
    }

    private fun setupCameraSpinners() {
        // Camera spinner
        val cameraStrings = resources.getStringArray(R.array.camera_values)
        val cameraAdapter = ArrayAdapter.createFromResource(this, R.array.camera_names, android.R.layout.simple_spinner_item)
        cameraAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cameras.adapter = cameraAdapter

        cameras.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>) {
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                if (recycler_view.adapter != null && currentCameraPosition != position) {
                    (recycler_view.adapter as PhotoAdapter).filterCamera(cameraStrings[position])
                }

                currentCameraPosition = position
            }
        }
    }

    private fun loadPhotos() {

        progress.visibility = View.VISIBLE
        recycler_view.visibility = View.GONE

        NasaPhotos.getPhotos(currentRover).enqueue(object : Callback<PhotoList> {
            override fun onFailure(call: Call<PhotoList>?, t: Throwable?) {
                Snackbar.make(recycler_view, R.string.api_error, Snackbar.LENGTH_LONG)
                Log.e(TAG, "Problems getting Photos with error: $t.msg")
            }

            override fun onResponse(call: Call<PhotoList>?, response: Response<PhotoList>?) {
                response?.let { photoResponse ->
                    if (photoResponse.isSuccessful) {
                        val body = photoResponse.body()
                        body?.let {
                            Log.d(TAG, "Received ${body.photos.size} photos")
                            if (recycler_view.adapter == null) {
                                val adapter = PhotoAdapter(sortPhotos(body))
                                recycler_view.adapter = adapter

                                // Handling the swipe movement
                                val touchHandler = ItemTouchHelper(SwipeHandler(adapter, 0, (ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)))
                                touchHandler.attachToRecyclerView(recycler_view)


                            } else {
                                (recycler_view.adapter as PhotoAdapter).updatePhotos(sortPhotos(body))
                            }
                        }
                        recycler_view.scrollToPosition(0)
                        recycler_view.visibility = View.VISIBLE
                        progress.visibility = View.GONE
                    }
                }
            }
        })
    }

    fun sortPhotos(photoList: PhotoList): ArrayList<PhotoRow> {

        val map = HashMap<String, ArrayList<Photo>>()

        for (photo in photoList.photos) {

            var photos = map[photo.camera.full_name]

            if (photos == null) {
                photos = ArrayList()
                map[photo.camera.full_name] = photos
            }
            photos.add(photo)
        }

        val newPhotos = ArrayList<PhotoRow>()

        for ((key, value) in map) {

            newPhotos.add(PhotoRow(RowType.HEADER, null, key))
            value.mapTo(newPhotos) { PhotoRow(RowType.PHOTO, it, null) }
        }

        return newPhotos
    }

    // Swipe movement hadlers
    class SwipeHandler(val adapter: PhotoAdapter, dragDirs: Int, swipeDirs: Int) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }


        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            adapter.removeRow(viewHolder.adapterPosition)
        }
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
