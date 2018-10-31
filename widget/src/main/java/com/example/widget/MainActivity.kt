package com.example.widget

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.example.widget.coffeeapp.CoffeeLoggerPersistence
import com.example.widget.coffeeapp.CoffeeLoggerWidget
import com.example.widget.coffeeapp.CoffeeTypes
import com.example.widget.coffeeapp.Constants

import kotlinx.android.synthetic.main.activity_main.*

// https://google-developer-training.gitbooks.io/android-developer-advanced-course-practicals/unit-1-expand-the-user-experience/lesson-2-app-widgets/2-1-p-app-widgets/2-1-p-app-widgets.html
//
//https://www.raywenderlich.com/178500/android-app-widgets-tutorial

class MainActivity : AppCompatActivity() {

    internal val coffeeLoggerPersistence = CoffeeLoggerPersistence(this)
    private var today: Int = 0
    private var gramsValue: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        gramsValue = findViewById(R.id.grams)
        refreshTodayLabel()

        if (intent != null && intent.action == Constants.ADD_COFFEE_INTENT) {
            val coffeeIntake = intent.getIntExtra(Constants.GRAMS_EXTRA, 0)
            coffeeLoggerPersistence.saveTitlePref(today + coffeeIntake)
            saveCoffeeIntake(coffeeIntake)
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    fun onRistrettoPressed(view: View) {
        coffeeLoggerPersistence.saveTitlePref(today + CoffeeTypes.RISTRETTO.grams)
        saveCoffeeIntake(CoffeeTypes.RISTRETTO.grams)
    }

    fun onEspressoPressed(view: View) {
        coffeeLoggerPersistence.saveTitlePref(today + CoffeeTypes.ESPRESSO.grams)
        saveCoffeeIntake(CoffeeTypes.ESPRESSO.grams)
    }

    fun onLongPressed(view: View) {
        coffeeLoggerPersistence.saveTitlePref(today + CoffeeTypes.LONG.grams)
        saveCoffeeIntake(CoffeeTypes.LONG.grams)
    }

    private fun refreshTodayLabel() {
        //Send the Broadcast so that the OS can update the widget
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val ids = appWidgetManager.getAppWidgetIds(ComponentName(this, CoffeeLoggerWidget::class.java))
        val updateIntent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        sendBroadcast(updateIntent)

        today = coffeeLoggerPersistence.loadTitlePref()
        gramsValue?.text = today.toString()
    }

    private fun saveCoffeeIntake(coffeeIntake: Int) {

        val mySnackBar = Snackbar.make(findViewById(R.id.main_coordinator_layout), R.string.intake_saved, Snackbar.LENGTH_LONG)
        mySnackBar.setAction(R.string.undo_string, MyUndoListener(coffeeIntake))
        mySnackBar.show()

        refreshTodayLabel()
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

    inner class MyUndoListener(private val intake: Int) : View.OnClickListener {
        override fun onClick(v: View) {
            coffeeLoggerPersistence.saveTitlePref(today - intake)
            refreshTodayLabel()
        }
    }
}