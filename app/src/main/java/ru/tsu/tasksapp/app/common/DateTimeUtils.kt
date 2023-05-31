package ru.tsu.tasksapp.app.common

import java.text.DateFormat
import java.util.*

object DateTimeUtils {
    private val calendar by lazy { Calendar.getInstance() }
    private val dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM)

    fun atEndOfDay(timestamp: Long): Long {
        calendar.timeInMillis = timestamp
        calendar[Calendar.HOUR_OF_DAY] = 23
        calendar[Calendar.MINUTE] = 59
        calendar[Calendar.SECOND] = 59
        calendar[Calendar.MILLISECOND] = 999
        return calendar.timeInMillis
    }

    fun atStartOfDay(timestamp: Long): Long {
        calendar.timeInMillis = timestamp
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        return calendar.timeInMillis
    }

    fun getNextDayTimestamp(timestamp: Long, period: Int): Long {
        calendar.timeInMillis = timestamp
        calendar.add(Calendar.DATE, period)
        return calendar.timeInMillis
    }

    fun getNextWeekTimestamp(timestamp: Long, period: Int): Long {
        calendar.timeInMillis = timestamp
        calendar.add(Calendar.WEEK_OF_YEAR, period)
        return calendar.timeInMillis
    }

    fun getNextMonthTimestamp(timestamp: Long, period: Int): Long {
        calendar.timeInMillis = timestamp
        calendar.add(Calendar.MONTH, period)
        return calendar.timeInMillis
    }

    fun getDateString(timestamp: Long) = dateFormatter.format(timestamp)
}