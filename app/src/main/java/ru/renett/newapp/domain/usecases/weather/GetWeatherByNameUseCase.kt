package ru.renett.newapp.domain.usecases.weather

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.renett.newapp.domain.models.CityDetailedWeather
import ru.renett.newapp.domain.repository.RepositoryException
import ru.renett.newapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherByNameUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(city: String): CityDetailedWeather? {
        return try {
            withContext(dispatcher) {
                weatherRepository.getWeatherInCityByName(city)
            }
        } catch (e: RepositoryException) {
            null
        }
    }
}
