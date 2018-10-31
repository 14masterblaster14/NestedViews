package com.example.widget.coffeeapp

import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.IBinder

class CoffeeQuotesService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val appWidgetManager = AppWidgetManager.getInstance(this)
        val allWidgetIds = intent?.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS)

        if (allWidgetIds != null) {
            for (appWidgetId in allWidgetIds) {
                CoffeeLoggerWidget.updateAppWidget(this, appWidgetManager, appWidgetId)
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        //throw UnsupportedOperationException("Not yet implemented")
        return null
    }
}
