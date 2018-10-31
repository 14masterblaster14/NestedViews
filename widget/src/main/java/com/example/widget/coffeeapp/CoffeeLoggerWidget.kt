package com.example.widget.coffeeapp

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.example.widget.MainActivity

import com.example.widget.R

/**
 * Implementation of App Widget functionality.
 */
class CoffeeLoggerWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
        // Use service in case you are using  data from a server through periodic polling and push notification events
        /*val intent = Intent(context.applicationContext, CoffeeQuotesService::class.java)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)
        context.startService(intent)*/
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                     appWidgetId: Int) {

            val coffeeLoggerPersistence = CoffeeLoggerPersistence(context)
            //val widgetText = context.getString(R.string.appwidget_text)   //Default
            val widgetText = coffeeLoggerPersistence.loadTitlePref().toString()
            Log.i("MasterBlaster", "widgetText : ${widgetText}")


            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.coffee_logger_widget)
            views.setTextViewText(R.id.appwidget_text, widgetText)

            //Adding Listeners to coffee buttons of widget
            views.setOnClickPendingIntent(R.id.ristretto_button, getPendingIntent(context, CoffeeTypes.RISTRETTO.grams))
            views.setOnClickPendingIntent(R.id.espresso_button, getPendingIntent(context, CoffeeTypes.ESPRESSO.grams))
            views.setOnClickPendingIntent(R.id.long_button, getPendingIntent(context, CoffeeTypes.LONG.grams))

            views.setTextViewText(R.id.coffee_quote, getRandomQuotes(context))

            val limit = coffeeLoggerPersistence.getLimitPref(appWidgetId)
            Log.i("MasterBlaster", "limit : ${limit}")
            val background = if (limit <= widgetText.toInt()) R.drawable.background_overlimit else R.drawable.background
            views.setInt(R.id.widget_layout, "setBackgroundResource", background)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        // passing selected coffee, grams info to main activity, when user selects the coffee in widget
        // creating pending Intent for selected coffee
        private fun getPendingIntent(context: Context, value: Int): PendingIntent {
            val intent = Intent(context, MainActivity::class.java)
            intent.action = Constants.ADD_COFFEE_INTENT
            intent.putExtra(Constants.GRAMS_EXTRA, value)
            return PendingIntent.getActivity(context, value, intent, 0)
        }

        private fun getRandomQuotes(context: Context): String {

            val quotes = context.resources.getStringArray(R.array.coffee_texts)
            val random = Math.random() * quotes.size
            return quotes[random.toInt()].toString()
        }
    }
}

