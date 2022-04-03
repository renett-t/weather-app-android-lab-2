package ru.renett.newapp.domain.usecases.weather

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.renett.newapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherIconUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(iconTitle: String): String {
        return withContext(dispatcher) {
            weatherRepository.getWeatherIconURL(iconTitle)
        }
    }
}
