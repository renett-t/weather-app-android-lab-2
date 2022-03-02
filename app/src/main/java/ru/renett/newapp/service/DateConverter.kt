package ru.renett.newapp.service

import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale.ENGLISH

class DateConverter {

    fun convertDateToPrettyString(calendar: Calendar) : String {
        val dateFormat = SimpleDateFormat("dd MMM, EEE", ENGLISH)
        return dateFormat.format(calendar.time)
    }
}
