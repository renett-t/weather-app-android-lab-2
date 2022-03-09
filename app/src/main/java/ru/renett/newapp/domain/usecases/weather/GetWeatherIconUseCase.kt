package ru.renett.newapp.domain.usecases.weather

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.renett.newapp.domain.models.CityDetailedWeather
import ru.renett.newapp.domain.repository.WeatherRepository

class GetWeatherIconUseCase(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(iconTitle: String): String {
        return withContext(dispatcher) {
            weatherRepository.getWeatherIconURL(iconTitle)
        }
    }
}
