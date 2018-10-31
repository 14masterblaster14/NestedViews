package com.example.accessibility

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

//https://google-developer-training.gitbooks.io/android-developer-advanced-course-practicals/unit-3-make-your-apps-accessible/lesson-6-accessibility/6-2-p-creating-accessible-apps/6-2-p-creating-accessible-apps.html
//Use Accessibility scanner to identify the problematic elements

class MainActivity : AppCompatActivity() {

    private var countInt: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()

            var countString: String = textView_count.text.toString()
            var stringArray: List<String> = countString.split(" ")
            Log.i("masterBlaster", "stringArray  : ${stringArray}")
            countString.let {

                countInt = stringArray[0].toInt()
                countInt += 1
            }

            textView_count.text = getString(R.string.count_format, countInt, 10)
            textView_count.announceForAccessibility(getString(R.string.count_updated, getString(R.string.count_format, countInt, 10)))
            /*
              If you are updating any view which is having accessibility feature then you can make that view
              read out through programs via "announceForAccessibility()" on a view

             amountConsumed.announceForAccessibility(getString(R.string.count_updated, consumedString()))

             <string name="count_updated">Count updated %s</string>

             */
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
