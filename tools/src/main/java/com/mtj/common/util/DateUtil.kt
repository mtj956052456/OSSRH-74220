package com.mtj.common.util

import java.text.SimpleDateFormat
import java.util.*


object DateUtil {

    fun fmDate(firstOnTime: Long, format: String): String = SimpleDateFormat(format).format(Date(firstOnTime))

    fun stringToDate(strTime: String, format: String): Date {
        var date: Date? =  Date()
        try {
            date = SimpleDateFormat(format).parse(strTime)
        } catch (e: Exception) {
        }
        return date ?: Date()
    }

    fun stringToLong(strTime: String, format: String): Long = stringToDate(strTime, format).time
}
