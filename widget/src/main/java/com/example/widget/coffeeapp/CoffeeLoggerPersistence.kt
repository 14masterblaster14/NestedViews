package com.example.widget.coffeeapp

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

/**
 */
class CoffeeLoggerPersistence(private val context: Context) {

    companion object {
        private const val PREFS_NAME = "com.example.android.coffeelogs.CoffeeLoggerWidget"
        private const val PREF_PREFIX_KEY = "coffee_logger"
        private const val PREF_LIMIT_PREFIX = "coffee_limit_"
    }

    private val strFormatter = SimpleDateFormat("DDMMYYYY", Locale.US)

    // Write the prefix to the SharedPreferences object for this widget
    internal fun saveTitlePref(value: Int) {
        val date = Date()
        val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
        prefs.putInt(PREF_PREFIX_KEY + strFormatter.format(date), value)
        prefs.apply()
    }

    // Write the prefix to the SharedPreferences object for this widget
    internal fun saveLimitPref(value: Int, widgetId: Int) {
        val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
        prefs.putInt(PREF_LIMIT_PREFIX + widgetId, value)
        prefs.apply()
    }

    // Write the prefix to the SharedPreferences object for this widget
    internal fun getLimitPref(widgetId: Int): Int {
        val prefs = context.getSharedPreferences(PREFS_NAME, 0)
        return prefs.getInt(PREF_LIMIT_PREFIX + widgetId, 0)
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    internal fun loadTitlePref(): Int {
        val date = Date()
        val prefs = context.getSharedPreferences(PREFS_NAME, 0)
        return prefs.getInt(PREF_PREFIX_KEY + strFormatter.format(date), 0)
    }

    internal fun deletePref(widgetId: Int) {
        val date = Date()
        val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
        prefs.remove(PREF_PREFIX_KEY + strFormatter.format(date))
        prefs.remove(PREF_LIMIT_PREFIX + widgetId)
        prefs.apply()
    }
}