package ru.renett.newapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.renett.newapp.domain.models.CityDetailedWeather
import ru.renett.newapp.domain.usecases.weather.GetWeatherByIdUseCase
import ru.renett.newapp.domain.usecases.weather.GetWeatherIconUseCase
import javax.inject.Inject

@HiltViewModel
class WeatherDetailsViewModel @Inject constructor(
    private val getCityWeatherById: GetWeatherByIdUseCase,
    private val getWeatherIcon: GetWeatherIconUseCase
) : ViewModel() {

    private var _weatherDetails: MutableLiveData<Result<CityDetailedWeather>> = MutableLiveData()
    val weatherDetails: LiveData<Result<CityDetailedWeather>> = _weatherDetails

    fun requestWeatherInCity(id: Int) {
        viewModelScope.launch {
            try {
                val weather = getCityWeatherById(id)
                if (weather != null) {
                    weather.icon = getWeatherIcon(weather.icon)
                    _weatherDetails.value = Result.success(weather)
                } else {
                    _weatherDetails.value = Result.failure(NullPointerException("No weather for city found "))
                }
            } catch (ex : Exception) {
                _weatherDetails.value = Result.failure(ex)
            }
        }
    }
}
