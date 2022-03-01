package ru.renett.newapp.service

import android.content.Context
import ru.renett.newapp.data.responce.Coordinates

private const val DEFAULT_LATITUDE = 55.776132
private const val DEFAULT_LONGITUDE = 49.143041

class LocationService(applicationContext: Context?) {
    //
    fun getCoordinates(): Coordinates {
        return getDefaultCoordinates()
    }

    fun getDefaultCoordinates() : Coordinates {
        return Coordinates(DEFAULT_LATITUDE, DEFAULT_LONGITUDE)
    }
}
