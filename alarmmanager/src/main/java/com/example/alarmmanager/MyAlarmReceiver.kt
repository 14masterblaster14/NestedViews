package com.example.alarmmanager

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Icon
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.widget.Toast

class MyAlarmReceiver : BroadcastReceiver() {

    companion object {

        private const val Group_KEY = "alarm_notify_group_key"
        private const val NOTIFICATION_ID = 7777
        private const val NOTIFICATION_ID1 = 7778
        private const val NOTIFICATION_ID2 = 7779
        private const val NOTIFICATION_ID3 = 7780
        private const val NOTIFICATION_ID_O = "com.example.notification_myalarm"
        private const val NOTIFICATION_NAME_O = "Alarm Notification"
        private const val NOTIFICATION_DESCRIPTION_O = "This is alarm example"
    }

    /*private val mNotificationManager: NotificationManager by lazy {
       getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
   }*/

    private lateinit var mNotificationManager: NotificationManager
    private val channelId by lazy { "com.example.notification_myalarm" }


    override fun onReceive(context: Context, intent: Intent) {
        // an Intent broadcast.
        //throw UnsupportedOperationException("Not yet implemented")
        Log.i("MasterBlaster", "Received Intent : ${intent}")

        mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val contentIntent = Intent(context, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            deliverPostOreoNotification(context, contentPendingIntent)

        } else {

            deliverPreOreoNotification(context, contentPendingIntent)
        }

        Toast.makeText(context, "Alarm received", Toast.LENGTH_LONG).show()

    }

    private fun deliverPreOreoNotification(context: Context, contentPendingIntent: PendingIntent) {

        val notification = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_stand_up)
                .setContentTitle("Stand Up Alert")
                .setContentText("You should stand up and walk around now!")
                /*   Expandable Notification
                .setLargeIcon(aBitmap)          //Need a bitmap image to show expandable notification
                .setStyle(NotificationCompat
                .BigPictureStyle()
                .bigPicture(aBigBitmap)
                .setBigContentTitle("Large Notification Title"))    */
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .build()

        mNotificationManager.notify(NOTIFICATION_ID, notification)
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun deliverPostOreoNotification(context: Context, contentPendingIntent: PendingIntent) {

        // We can also add the actions in notification message
        // i.e. buttons buttons beneath the notification message and are programmed to trigger
        // specific intents when tapped by the user

        val icon = Icon.createWithResource(context, android.R.drawable.ic_dialog_info)
        val action = Notification.Action.Builder(icon, "Open", contentPendingIntent).build()


        createNotificationChannel(NOTIFICATION_ID_O, NOTIFICATION_NAME_O, NOTIFICATION_DESCRIPTION_O)

        val notification = Notification.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_stand_up)
                .setContentTitle("Stand Up Alert")
                .setContentText("You should stand up and walk around now!")
                .setChannelId(channelId)
                .setNumber(7)
                .setContentIntent(contentPendingIntent)
                .setActions(action)
                .setAutoCancel(true)
                .build()

        mNotificationManager.notify(NOTIFICATION_ID, notification)


        // If sending multiple notification then they should be grouped together to avoid clutter

        /* val notification1 = Notification.Builder(context,channelId)
                 .setSmallIcon(android.R.drawable.ic_dialog_info)
                 .setContentTitle("Stand Up Alert1")
                 .setContentText("You should stand up and walk around now! - By Alpha")
                 .setGroup(Group_KEY)
                 .build()

         val notification2 = Notification.Builder(context,channelId)
                 .setSmallIcon(android.R.drawable.ic_dialog_info)
                 .setContentTitle("Stand Up Alert1")
                 .setContentText("You should stand up and walk around now! - By Beta")
                 .setGroup(Group_KEY)
                 .build()

         val notificationSummary = Notification.Builder(context,channelId)
                 .setSmallIcon(android.R.drawable.ic_dialog_info)
                 .setContentTitle("Stand Up Alerts")
                 .setContentText("You have 2 stand up alerts")
                 .setGroup(Group_KEY)
                 .setGroupSummary(true)
                 .build()

         mNotificationManager.notify(NOTIFICATION_ID1,notification1)
         mNotificationManager.notify(NOTIFICATION_ID2,notification2)
         mNotificationManager.notify(NOTIFICATION_ID3,notificationSummary)*/
    }


    @TargetApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(id: String, name: String, description: String) {

        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(id, name, importance)
        channel.description = description
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(true)
        channel.setShowBadge(true)
        channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)

        mNotificationManager.createNotificationChannel(channel)
    }
}
