package ru.renett.newapp.domain.usecases.weather

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.renett.newapp.domain.models.CitySimpleWeather
import ru.renett.newapp.domain.models.Coordinates
import ru.renett.newapp.domain.repository.RepositoryException
import ru.renett.newapp.domain.repository.WeatherRepository

class GetSimpleWeatherForCitiesUseCase(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(coordinates: Coordinates, cityCount: Int): MutableList<CitySimpleWeather> {
        return try {
            withContext(dispatcher) {
                weatherRepository.getWeatherInNearCities(coordinates, cityCount)
            }
        } catch (e: RepositoryException) {
            ArrayList<CitySimpleWeather>()
        }
    }
}
