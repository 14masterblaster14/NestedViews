package com.example.widget.samplewidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.RemoteViews
import com.example.widget.R
import java.text.DateFormat
import java.util.*

/**
 * Implementation of App Widget functionality.
 *
 * Here we choose type : Home screen and Keyguard i.e. lock screen (display type)
 */


class NewAppWidget : AppWidgetProvider() {

    /*
     * Override for onUpdate() method, to handle all widget update requests.
     *
     * @param context          The application context.
     * @param appWidgetManager The app widget manager.
     * @param appWidgetIds     An array of the app widget IDs.
     */

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
        // like opening new database
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
        // like clean up any resources that were created in onEnabled()
    }

    companion object {

        // for current update count
        private const val mSharedPrefFile = "com.example.android.newappwidget"
        private const val COUNT_KEY = "count"

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                     appWidgetId: Int) {

            // Getting count and updating it
            val sharedPreferences = context.getSharedPreferences(mSharedPrefFile, 0)
            var count = sharedPreferences.getInt(COUNT_KEY + appWidgetId, 0)
            count++
            // Get the current time.
            var dateString = DateFormat.getTimeInstance(DateFormat.SHORT).format(Date())
            Log.i("MasterBlaster", "dateString : ${dateString}")


            //val widgetText = context.getString(R.string.appwidget_text)       // Default

            // Construct the RemoteViews object
            var views = RemoteViews(context.packageName, R.layout.new_app_widget)
            //views.setTextViewText(R.id.appwidget_text, widgetText)            //Default

            views.setTextViewText(R.id.appwidget_id, appWidgetId.toString())
            views.setTextViewText(R.id.appwidget_update,
                    context.resources.getString(R.string.date_count_format, count, dateString))

            //Updating the count
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putInt(COUNT_KEY + appWidgetId, count)
            editor.apply()


            // Setup update button to send an update request as a pending intent.
            var intentUpdate = Intent(context, NewAppWidget::class.java)

            // The intent action must be an app widget update.
            intentUpdate.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE

            // Include the widget ID to be updated as an intent extra.
            var idArray: IntArray = intArrayOf(appWidgetId)
            intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray)

            // Wrap it all in a pending intent to send a broadcast.
            // Use the app widget ID as the request code (third argument) so that
            // each intent is unique.
            var pendingUpdateIntent: PendingIntent = PendingIntent.getBroadcast(context,
                    appWidgetId, intentUpdate, PendingIntent.FLAG_UPDATE_CURRENT)

            // Assign the pending intent to the button onClick handler
            views.setOnClickPendingIntent(R.id.button_update, pendingUpdateIntent)


            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

