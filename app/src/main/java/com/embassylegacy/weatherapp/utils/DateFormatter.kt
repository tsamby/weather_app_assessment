package com.embassylegacy.weatherapp.utils

import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*


object DateFormatter {
    /**
     * Get what day of week it is i.e, Sunday, Monday e.t.c
     */
    fun getDayOfWeek(time: Long?): String {
        val date = Date(time!!)
        return DateFormat.format("EEEE", date) as String
    }

    fun getDayOfWeek(date: String?, dayCount: Int): String? {
        var date = date
        val inputFormat = "yyyy-MM-dd HH:mm:ss"
        val parsed: Date
        val input = SimpleDateFormat(inputFormat, Locale.getDefault())
        try {
            parsed = input.parse(date)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd EEEE", Locale.getDefault())
            if (parsed != null) {
                date = dateFormat.format(parsed.time + dayCount * 24 * 60 * 60 * 1000)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return date
    }

    /**
     * Return the actual day of month e.g 15th (of March)
     */
    fun getDay(date: Date?): String {
        return DateFormat.format("dd", date) as String
    }

    /**
     * Get the month
     */
    fun getMonth(date: Date?): String {
        return DateFormat.format("MMMM", date) as String
    }

    /**
     * Return the time -> 04:16 pm
     */
    private fun getTime(date: Date): String {
        return DateFormat.format("HH:mm", date) as String
    }

    /**
     * Get a string combination of month, day and time
     */
    fun getTimeSinceLastUpdate(time: Long?): String {
        val date = Date(time!!)
        return getMonth(date) + " " + getDay(date) + ", " + getTime(date)
    }
}