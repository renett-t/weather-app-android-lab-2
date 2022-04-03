package ru.renett.newapp.data.repositories

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import ru.renett.newapp.domain.models.Coordinates
import ru.renett.newapp.domain.repository.LocationRepository
import javax.inject.Inject

private const val DEFAULT_LATITUDE = 55.776132
private const val DEFAULT_LONGITUDE = 49.143041

class LocationRepositoryImpl @Inject constructor(
    private val locationClient: FusedLocationProviderClient
) : LocationRepository {

    @SuppressLint("MissingPermission")
    override fun getUserCurrentCoordinates(): Coordinates? {
        var coordinates: Coordinates? = null
        locationClient.lastLocation.addOnSuccessListener { location: Location? ->
            coordinates = if (location == null) {
                return@addOnSuccessListener
            } else {
                Coordinates(location.latitude, location.longitude)
            }
        }

        Log.e("Location", "Current coordinates: ${coordinates?.lat}, ${coordinates?.lon}")
        return coordinates
    }

    override fun getDefaultCoordinates() : Coordinates {
        return Coordinates(DEFAULT_LATITUDE, DEFAULT_LONGITUDE)
    }
}
