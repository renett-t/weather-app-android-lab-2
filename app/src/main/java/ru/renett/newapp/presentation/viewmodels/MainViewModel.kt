package ru.renett.newapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.renett.newapp.domain.models.CityDetailedWeather
import ru.renett.newapp.domain.models.CitySimpleWeather
import ru.renett.newapp.domain.models.Coordinates
import ru.renett.newapp.domain.usecases.location.GetLocationUseCase
import ru.renett.newapp.domain.usecases.weather.GetSimpleWeatherForCitiesUseCase
import ru.renett.newapp.domain.usecases.weather.GetWeatherByNameUseCase
import ru.renett.newapp.domain.usecases.weather.GetWeatherIconUseCase
import java.lang.IllegalArgumentException

class MainViewModel(
    private val getCitiesWeather: GetSimpleWeatherForCitiesUseCase,
    private val getWeatherIcon: GetWeatherIconUseCase,
    private val getCityWeatherByName: GetWeatherByNameUseCase,
    private val getLocation: GetLocationUseCase,
) : ViewModel() {

    private var _location: MutableLiveData<Result<Coordinates>> = MutableLiveData()
    val location: LiveData<Result<Coordinates>> = _location

    private var _citiesWeather: MutableLiveData<Result<MutableList<CitySimpleWeather>>> = MutableLiveData()
    val citiesWeather: LiveData<Result<MutableList<CitySimpleWeather>>> = _citiesWeather

    private var _cityWeather: SingleLiveEvent<Result<CityDetailedWeather>> = SingleLiveEvent<Result<CityDetailedWeather>>()
    val cityWeather: SingleLiveEvent<Result<CityDetailedWeather>> = _cityWeather

    fun requestLocation() {
        viewModelScope.launch {
            try {
                val coordinates = getLocation()
                _location.value = Result.success(coordinates)
            } catch (ex : Exception) {
                _location.value = Result.failure(ex)
            }
        }
    }

    fun requestCitiesWeather(coordinates: Coordinates, cityCount: Int) {
        viewModelScope.launch {
            try {
                val list = getCitiesWeather(coordinates, cityCount)
                for (item in list) {
                    item.icon = getWeatherIcon(item.icon)
                }
                _citiesWeather.value = Result.success(list)
            } catch (ex : Exception) {
                _citiesWeather.value = Result.failure(ex)
            }
        }
    }

    fun requestCityWeatherByName(title: String) {
        viewModelScope.launch {
            try {
                val city = getCityWeatherByName(title)
                if (city != null) {
                    _cityWeather.value = Result.success(city)
                } else {
                    _cityWeather.value = Result.failure(NullPointerException("No city '${city}' found "))
                }
            } catch (ex : Exception) {
                val message = "No weather info about '${title}' found"
                _cityWeather.value = Result.failure(IllegalArgumentException(message, ex))
            }
        }
    }

}

