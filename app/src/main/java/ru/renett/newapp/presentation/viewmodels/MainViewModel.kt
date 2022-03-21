package ru.renett.newapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import ru.renett.newapp.domain.usecases.location.GetLocationUseCase
import ru.renett.newapp.domain.usecases.weather.GetSimpleWeatherForCitiesUseCase
import ru.renett.newapp.domain.usecases.weather.GetWeatherByNameUseCase
import ru.renett.newapp.domain.usecases.weather.GetWeatherIconUseCase

class MainViewModel(
    private val getCitiesWeather: GetSimpleWeatherForCitiesUseCase,
    private val getWeatherIcon: GetWeatherIconUseCase,
    private val getCityWeatherByName: GetWeatherByNameUseCase,
    private val getLocation: GetLocationUseCase,
) : ViewModel() {

}

