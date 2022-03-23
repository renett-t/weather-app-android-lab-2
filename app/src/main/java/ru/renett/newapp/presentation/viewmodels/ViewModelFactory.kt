package ru.renett.newapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.renett.newapp.domain.usecases.location.GetLocationUseCase
import ru.renett.newapp.domain.usecases.weather.GetSimpleWeatherForCitiesUseCase
import ru.renett.newapp.domain.usecases.weather.GetWeatherByIdUseCase
import ru.renett.newapp.domain.usecases.weather.GetWeatherByNameUseCase
import ru.renett.newapp.domain.usecases.weather.GetWeatherIconUseCase
import java.lang.IllegalArgumentException

class ViewModelFactory (
    private val getCitiesWeather: GetSimpleWeatherForCitiesUseCase,
    private val getCityWeatherByName: GetWeatherByNameUseCase,
    private val getLocation: GetLocationUseCase,
    private val getCityWeatherById: GetWeatherByIdUseCase,
    private val getWeatherIcon: GetWeatherIconUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) ->
                MainViewModel(getCitiesWeather,getWeatherIcon, getCityWeatherByName, getLocation)
                    as? T ?: throw IllegalArgumentException("Unknown ViewModel class")
            modelClass.isAssignableFrom(WeatherDetailsViewModel::class.java) ->
                WeatherDetailsViewModel(getCityWeatherById,getWeatherIcon)
                        as? T ?: throw IllegalArgumentException("Unknown ViewModel class")
            else ->
                throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
