package com.example.notifications

import android.annotation.TargetApi
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.example.notifications.NotificationHelper.Companion.NOTIFICATION_COWORKER
import com.example.notifications.NotificationHelper.Companion.NOTIFICATION_FOLLOW
import com.example.notifications.NotificationHelper.Companion.NOTIFICATION_FRIEND
import com.example.notifications.NotificationHelper.Companion.NOTIFICATION_FRIENDS
import com.example.notifications.NotificationHelper.Companion.NOTIFICATION_UNFOLLOW
import com.example.notifications.R.id.*

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mNotificationHelper: NotificationHelper

    private lateinit var notificationIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        mNotificationHelper = NotificationHelper(this)

        follow_button.setOnClickListener { sendNotification(NOTIFICATION_FOLLOW) }
        unfollow_button.setOnClickListener { sendNotification(NOTIFICATION_UNFOLLOW) }
        follower_channel_settings.setOnClickListener { gotoNotificationChannelSettings(NotificationHelper.FOLLOWERS_CHANNEL) }

        friend_message_button.setOnClickListener { sendNotification(NOTIFICATION_FRIEND) }
        coworker_message_button.setOnClickListener { sendNotification(NOTIFICATION_COWORKER) }
        app_notification_settings.setOnClickListener { gotoNotificationSettings() }

        grouping.setOnClickListener { sendNotification(NOTIFICATION_FRIENDS) }

    }


    private fun sendNotification(notificationId: Int) {

        when (notificationId) {

            NOTIFICATION_FOLLOW -> mNotificationHelper.notify(notificationId)

            NOTIFICATION_UNFOLLOW -> mNotificationHelper.notify(notificationId)

            NOTIFICATION_FRIEND -> mNotificationHelper.notify(notificationId)

            NOTIFICATION_COWORKER -> mNotificationHelper.notify(notificationId)

            NOTIFICATION_FRIENDS -> mNotificationHelper.notify(notificationId)
        }

    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun gotoNotificationChannelSettings(notificationChannel: String) {

        val channelIntent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
                .putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                .putExtra(Settings.EXTRA_CHANNEL_ID, notificationChannel)
        startActivity(channelIntent)
    }

    private fun gotoNotificationSettings() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            notificationIntent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                    .putExtra(Settings.EXTRA_APP_PACKAGE, packageName)

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            notificationIntent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS) //"android.settings.APP_NOTIFICATION_SETTINGS"
                    .putExtra("app_package", packageName)
                    .putExtra("app_uid", applicationInfo.uid)

        } else if ((Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT)) {

            notificationIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    .addCategory(Intent.CATEGORY_DEFAULT)
                    .setData(Uri.parse(("package:+$packageName")))
        }

        startActivity(notificationIntent)
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
