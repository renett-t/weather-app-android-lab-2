package ru.renett.newapp.domain.usecases.location

import ru.renett.newapp.domain.models.Coordinates
import ru.renett.newapp.domain.repository.LocationRepository

class GetLocationUseCase(
    private val locationRepository: LocationRepository
) {

    operator fun invoke() : Coordinates {
        return locationRepository.getUserCurrentCoordinates() ?:
            locationRepository.getDefaultCoordinates()
    }
}
