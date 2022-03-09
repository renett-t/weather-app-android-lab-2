package ru.renett.newapp.domain.usecases.weather

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.renett.newapp.domain.models.CityDetailedWeather
import ru.renett.newapp.domain.repository.RepositoryException
import ru.renett.newapp.domain.repository.WeatherRepository

class GetWeatherByIdUseCase(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(id: Int): CityDetailedWeather? {
        return try {
            withContext(dispatcher) {
                weatherRepository.getWeatherInCityById(id)
            }
        } catch (e: RepositoryException) {
            null
        }
    }
}
