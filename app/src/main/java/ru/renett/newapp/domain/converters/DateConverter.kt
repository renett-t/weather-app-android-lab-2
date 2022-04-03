package ru.renett.newapp.domain.converters

import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale.ENGLISH
import javax.inject.Inject

class DateConverter @Inject constructor() {

    fun convertDateToPrettyString(calendar: Calendar) : String {
        val dateFormat = SimpleDateFormat("dd MMM, EEE", ENGLISH)
        return dateFormat.format(calendar.time)
    }
}
