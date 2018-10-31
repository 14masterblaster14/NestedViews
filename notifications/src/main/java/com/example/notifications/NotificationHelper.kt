package com.example.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Icon
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat

internal class NotificationHelper(var context: Context) : ContextWrapper(context) {

    companion object {

        // Notification Channel ID
        const val FOLLOWERS_CHANNEL = "follower"
        const val DIRECT_MESSAGE_CHANNEL = "direct_message"

        // Notification ID
        const val NOTIFICATION_FOLLOW = 1100
        const val NOTIFICATION_UNFOLLOW = 1101
        const val NOTIFICATION_FRIEND = 1200
        const val NOTIFICATION_COWORKER = 1201

        // Grouping


        const val Group_KEY = "alarm_notify_group_key"
        const val NOTIFICATION_FRIENDS = 1400
        const val NOTIFICATION_FRIEND1 = 1401
        const val NOTIFICATION_FRIEND2 = 1402
        const val NOTIFICATION_FRIEND3 = 1403
    }

    private val mNotificationManager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private lateinit var contentIntent: Intent
    private lateinit var contentPendingIntent: PendingIntent
    private var isPostOreoNotification = false

    fun notify(notificationId: Int) {

        contentPendingIntent = getPendingIntent(notificationId)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            isPostOreoNotification = true

            deliverPostOreoNotification(notificationId)

        } else {

            deliverPreOreoNotification(notificationId)
        }
    }

    private fun getPendingIntent(notificationId: Int): PendingIntent {

        when {

            (notificationId == NOTIFICATION_FOLLOW || notificationId == NOTIFICATION_UNFOLLOW) -> {
                contentIntent = Intent(context, NextActivity::class.java)
            }

            (notificationId == NOTIFICATION_FRIEND || notificationId == NOTIFICATION_COWORKER || notificationId == NOTIFICATION_FRIENDS) -> {
                contentIntent = Intent(context, NextActivity::class.java)
            }
        }

        return PendingIntent.getActivity(context, notificationId, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun deliverPreOreoNotification(notificationId: Int) {

        when (notificationId) {

            NOTIFICATION_FOLLOW -> buildNotification(notificationId, getString(R.string.follower_title_notification), getString(R.string.follower_added_notification_body, "Master"))
            NOTIFICATION_UNFOLLOW -> buildNotification(notificationId, getString(R.string.follower_title_notification), getString(R.string.follower_removed_notification_body, "Master"))
            NOTIFICATION_FRIEND -> buildNotification(notificationId, getString(R.string.direct_message_title_notification), getString(R.string.dm_friend_notification_body, "Blaster"))
            NOTIFICATION_COWORKER -> buildNotification(notificationId, getString(R.string.direct_message_title_notification), getString(R.string.dm_coworker_notification_body, "Blaster"))
            NOTIFICATION_FRIENDS -> buildNotification(notificationId, getString(R.string.direct_message_title_notification), getString(R.string.dm_friend_notification_body, "Blaster"))
        }
    }


    private fun buildNotification(notificationId: Int, title: String, description: String) {

        val notification = NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setContentTitle(title)
                .setContentText(description)
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

        mNotificationManager.notify(notificationId, notification)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun deliverPostOreoNotification(notificationId: Int) {

        when {

            (notificationId == NOTIFICATION_FOLLOW || notificationId == NOTIFICATION_UNFOLLOW) -> {
                createFollowerNotificationChannel()

                if (notificationId == NOTIFICATION_FOLLOW) {
                    buildFollowerNotification(notificationId, getString(R.string.follower_title_notification), getString(R.string.follower_added_notification_body, "Master"))
                } else {
                    buildFollowerNotification(notificationId, getString(R.string.follower_title_notification), getString(R.string.follower_removed_notification_body, "Master"))
                }
            }

            (notificationId == NOTIFICATION_FRIEND || notificationId == NOTIFICATION_COWORKER) -> {
                createMessageNotificationChannel()

                if (notificationId == NOTIFICATION_FRIEND) {
                    buildMessageNotidfication(notificationId, getString(R.string.direct_message_title_notification), getString(R.string.dm_friend_notification_body, "Blaster"))
                } else {
                    buildMessageNotidfication(notificationId, getString(R.string.direct_message_title_notification), getString(R.string.dm_coworker_notification_body, "Blaster"))
                }
            }

            (notificationId == NOTIFICATION_FRIENDS) -> {
                createMessageNotificationChannel()
                buildBulkMessageNotidfication(notificationId, getString(R.string.direct_message_title_notification), getString(R.string.dm_friend_notification_body, "Blaster"))
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun buildFollowerNotification(notificationId: Int, title: String, description: String) {

        // We can also add the actions in notification message
        // i.e. buttons buttons beneath the notification message and are programmed to trigger
        // specific intents when tapped by the user

        val icon = Icon.createWithResource(context, android.R.drawable.ic_dialog_info)
        val action = Notification.Action.Builder(icon, "Open", contentPendingIntent).build()


        val notification = Notification.Builder(context, FOLLOWERS_CHANNEL)
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setContentTitle(title)
                .setContentText(description)
                .setChannelId(FOLLOWERS_CHANNEL)
                .setNumber(7)
                .setContentIntent(contentPendingIntent)
                .setActions(action)
                .setAutoCancel(true)
                .build()

        mNotificationManager.notify(notificationId, notification)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun buildMessageNotidfication(notificationId: Int, title: String, description: String) {

        // We can also add the actions in notification message
        // i.e. buttons buttons beneath the notification message and are programmed to trigger
        // specific intents when tapped by the user

        val icon = Icon.createWithResource(context, android.R.drawable.ic_dialog_info)
        val action = Notification.Action.Builder(icon, "Open", contentPendingIntent).build()


        val notification = Notification.Builder(context, DIRECT_MESSAGE_CHANNEL)
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setContentTitle(title)
                .setContentText(description)
                .setChannelId(DIRECT_MESSAGE_CHANNEL)
                .setNumber(7)
                .setContentIntent(contentPendingIntent)
                .setActions(action)
                .setAutoCancel(true)
                .build()

        mNotificationManager.notify(notificationId, notification)


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun buildBulkMessageNotidfication(notificationId: Int, title: String, description: String) {

        /*// We can also add the actions in notification message
        // i.e. buttons buttons beneath the notification message and are programmed to trigger
        // specific intents when tapped by the user

        val icon = Icon.createWithResource(context, android.R.drawable.ic_dialog_info)
        val action = Notification.Action.Builder(icon, "Open", contentPendingIntent).build()


        val notification = Notification.Builder(context, DIRECT_MESSAGE_CHANNEL)
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setContentTitle(title)
                .setContentText(description)
                .setChannelId(DIRECT_MESSAGE_CHANNEL)
                .setNumber(7)
                .setContentIntent(contentPendingIntent)
                .setActions(action)
                .setAutoCancel(true)
                .build()

        mNotificationManager.notify(notificationId, notification)*/


        // If sending multiple notification then they should be grouped together to avoid clutter

        val notification1 = Notification.Builder(context, DIRECT_MESSAGE_CHANNEL)
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setContentTitle("Alert1")
                .setContentText("Hi, this is alert1!")
                .setGroup(Group_KEY)
                .build()

        val notification2 = Notification.Builder(context, DIRECT_MESSAGE_CHANNEL)
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setContentTitle("Alert2")
                .setContentText("Hi, this is alert2!")
                .setGroup(Group_KEY)
                .build()

        val notificationSummary = Notification.Builder(context, DIRECT_MESSAGE_CHANNEL)
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setContentTitle("Alerts")
                .setContentText("You have 2 alerts")
                .setGroup(Group_KEY)
                .setGroupSummary(true)
                .build()

        mNotificationManager.notify(NOTIFICATION_FRIEND1, notification1)
        mNotificationManager.notify(NOTIFICATION_FRIEND2, notification2)
        mNotificationManager.notify(NOTIFICATION_FRIENDS, notificationSummary)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createFollowerNotificationChannel() {

        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(FOLLOWERS_CHANNEL, getString(R.string.notification_channel_followers), importance)
        channel.description = getString(R.string.notification_channel_followers)
        channel.enableLights(true)
        channel.lightColor = Color.GREEN
        channel.enableVibration(true)
        channel.setShowBadge(false)
        channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)

        mNotificationManager.createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createMessageNotificationChannel() {

        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(DIRECT_MESSAGE_CHANNEL, getString(R.string.notification_channel_direct_message), importance)
        channel.description = getString(R.string.notification_channel_direct_message)
        channel.enableLights(true)
        channel.lightColor = Color.BLUE
        channel.enableVibration(true)
        channel.setShowBadge(true)

        mNotificationManager.createNotificationChannel(channel)
    }
}