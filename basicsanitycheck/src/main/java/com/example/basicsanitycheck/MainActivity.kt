package com.example.basicsanitycheck

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog

import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var isDeviceRooted = false
    private var isDeviceConnected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        isDeviceRooted = DeviceRootCheckUtil.isDeviceRooted(this)
        isDeviceConnected = NetworkConnectionCheckUtil.isOnline(this)

        if (isDeviceRooted == false) {  // For testing set as false otherwise it should be true

            showDialog(MyDialog.TAG_DEVICE_ROOT_CHECK)
        }

        if (isDeviceConnected == false) {

            showDialog(MyDialog.TAG_DEVICE_CONNECTIVITY)

        }

        showDialog(MyDialog.TAG_CUSTOM)
    }


    private fun showDialog(tag: String?) {

        val myDialog = MyDialog()
        myDialog.show(this.supportFragmentManager, tag)
        // myDialog.show(this.fragmentManager,tag)

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
