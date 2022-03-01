package ru.renett.newapp.service

import kotlinx.coroutines.*
import ru.renett.newapp.data.WeatherRepository
import ru.renett.newapp.data.responce.CityWeatherData
import ru.renett.newapp.data.responce.Coordinates
import ru.renett.newapp.models.City

class WeatherService(
    private val weatherRepository: WeatherRepository
) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun getWeatherInNearCities(coordinates: Coordinates, cityCount: Int) : MutableList<City> {
        var cities = ArrayList<City>()
        val citiesDeferred: Deferred<ArrayList<City>> = scope.async {
            val citiesRaw = weatherRepository.getWeatherInNearCities(coordinates, cityCount)
            if (citiesRaw != null) {
                for (cityWeather in citiesRaw.listOfCitiesWeather) {
                    cities.add(
                        City(
                            cityWeather.id,
                            cityWeather.cityTitle,
                            cityWeather.weatherInfo.temperature
                        )
                    )
                }
            }
            cities
        }
        scope.launch() {
            cities = citiesDeferred.await()
        }
        return cities;
    }

    suspend fun getWeatherInCityByName(city: String) : CityWeatherData? {
        var cityWeatherData: CityWeatherData? = null

        val cityData: Deferred<CityWeatherData> = scope.async {
            weatherRepository.getWeatherInCityByName(city)
        }
        scope.launch {
            cityWeatherData = cityData.await()
        }

        return cityWeatherData
    }

    fun getWeatherInCityById(id: Long) : CityWeatherData? {
        var cityWeatherData: CityWeatherData? = null

        val cityData: Deferred<CityWeatherData> = scope.async {
            weatherRepository.getWeatherInCityById(id)
        }
        scope.launch {
            cityWeatherData = cityData.await()
        }

        return cityWeatherData
    }

    fun getWeatherIconURL(iconTitle: String) : String {
        var url = ""

        val urlData: Deferred<String> = scope.async {
            weatherRepository.getWeatherIconURL(iconTitle)
        }
        scope.launch {
            url = urlData.await()
        }

        return url
    }

}
