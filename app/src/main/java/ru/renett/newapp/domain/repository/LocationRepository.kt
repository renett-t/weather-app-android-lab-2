package ru.renett.newapp.domain.repository

import ru.renett.newapp.domain.models.Coordinates

interface LocationRepository {
    fun getUserCurrentCoordinates(): Coordinates?
    fun getDefaultCoordinates() : Coordinates
}
