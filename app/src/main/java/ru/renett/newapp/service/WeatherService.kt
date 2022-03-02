package ru.renett.newapp.service

import ru.renett.newapp.data.WeatherRepository
import ru.renett.newapp.data.responce.CityWeatherData
import ru.renett.newapp.data.responce.Coordinates
import ru.renett.newapp.models.City

class WeatherService(
    private val weatherRepository: WeatherRepository
) {
    suspend fun getWeatherInNearCities(coordinates: Coordinates, cityCount: Int) : MutableList<City> {
        val cities = ArrayList<City>()
        val citiesRaw = weatherRepository.getWeatherInNearCities(coordinates, cityCount)
        if (citiesRaw != null) {
            for (cityWeather in citiesRaw.listOfCitiesWeather) {
                cities.add(
                    City(
                        cityWeather.id,
                        cityWeather.cityTitle,
                        cityWeather.weatherInfo.temperature,
                        getWeatherIconURL(cityWeather.weatherState[0].icon)
                    )
                )
            }
        }

        return cities
    }

    suspend fun getWeatherInCityByName(city: String) : CityWeatherData? {
        return weatherRepository.getWeatherInCityByName(city)
    }

    suspend fun getWeatherInCityById(id: Int) : CityWeatherData? {
       return weatherRepository.getWeatherInCityById(id)
    }

    fun getWeatherIconURL(iconTitle: String) : String {
        return weatherRepository.getWeatherIconURL(iconTitle)
    }
}
