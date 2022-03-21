package ru.renett.newapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.renett.newapp.domain.models.CityDetailedWeather
import ru.renett.newapp.domain.usecases.weather.GetWeatherByIdUseCase
import ru.renett.newapp.domain.usecases.weather.GetWeatherIconUseCase

class WeatherDetailsViewModel(
    private val getCityWeatherById: GetWeatherByIdUseCase,
    private val getWeatherIcon: GetWeatherIconUseCase
) {
    private var _weatherDetails: MutableLiveData<Result<CityDetailedWeather>> = MutableLiveData()
    val weatherDetails: LiveData<Result<CityDetailedWeather>> = _weatherDetails
}
