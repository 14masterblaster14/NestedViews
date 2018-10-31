package com.example.alarmmanager

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


//  Refer below link for alarm scheduling service if need interval less than 15 min or want to wake up the mobile etc
//  https://medium.com/mindorks/android-scheduling-background-services-a-developers-nightmare-c573807c2705
//  https://medium.com/@benexus/background-services-in-android-o-862121d96c95

class MainActivity : AppCompatActivity() {

    companion object {
        private val NOTIFICATION_ID = 7777
        private const val ACTION_NOTIFY = "com.example.alarmmanager.standup.ACTION_NOTIFY"
    }

    private val alarmManager: AlarmManager by lazy {
        getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    private val mNotificationManager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private var toastMessage: String? = null
    private lateinit var alarmPendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }


        //LocalBroadcastManager.getInstance(this).registerReceiver(MyAlarmReceiver)

        var alarmIntent = Intent(ACTION_NOTIFY)
        alarmIntent.setClass(this, MyAlarmReceiver::class.java)
        alarmPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        alarmToggle.setOnCheckedChangeListener { buttonView, isChecked -> showToast(isChecked) }
    }


    private fun showToast(checked: Boolean) {

        if (checked) {

            // Generating the alarm
            generateAlarm()

            // Generating the notification
            //deliverNotification(this)

            //Set a toast message for the "ON" case
            toastMessage = "Stand Up!! Alarm Status : ON "

        } else {

            //Cancel
            alarmManager.cancel(alarmPendingIntent)

            //Cancel notification if the alarm is turned off
            mNotificationManager.cancelAll()

            //Set a toast message for the "OFF" case
            toastMessage = "Relax...Alarm Status : OFF "
        }

        //Show Toast to say the alarm is turned On or Off
        Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show()
    }

    private fun generateAlarm() {

        var triggerTime = SystemClock.elapsedRealtime() //+ AlarmManager.INTERVAL_FIFTEEN_MINUTES
        var repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            alarmManager.setAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, alarmPendingIntent)
            //alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerTime,alarmPendingIntent)

        } else {

            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, repeatInterval, alarmPendingIntent)
        }

        /*
            InexactRepeating: it is more resource efficient to use inexact timing
            (the system can bundle alarms from different apps together) and
            it is acceptable for your alarm to deviate a little bit from the exact 15 minute repeat interval.

            */
    }

    /*private fun deliverNotification(context: Context) {

        var contentIntent = Intent(context, MainActivity::class.java)
        var contentPendingIntent: PendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        var builder = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_stand_up)
                .setContentTitle("Stand Up Alert")
                .setContentText("You should stand up and walk around now!")
                *//*  Expandable Notification
                .setLargeIcon(aBitmap)      Need a bitmap image to show expandable notification
                .setStyle(NotificationCompat
                        .BigPictureStyle()
                        .bigPicture(aBigBitmap)
                        .setBigContentTitle("Large Notification Title"))*//*
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)

        mNotificationManager.notify(NOTIFICATION_ID, builder.build())

    }*/


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
