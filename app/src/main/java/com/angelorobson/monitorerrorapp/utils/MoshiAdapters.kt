package com.angelorobson.monitorerrorapp.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.*

var customDateAdapter: Any = object : Any() {

    var dateFormat: DateFormat? = null

    init {
        dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSS")
        (dateFormat as SimpleDateFormat).timeZone = TimeZone.getDefault()
    }

    @ToJson
    @Synchronized
    fun dateToJson(d: Date?): String? {
        return dateFormat?.format(d)
    }

    @FromJson
    @Synchronized
    @Throws(ParseException::class)
    fun dateToJson(s: String?): Date? {
        return dateFormat?.parse(s)
    }


}