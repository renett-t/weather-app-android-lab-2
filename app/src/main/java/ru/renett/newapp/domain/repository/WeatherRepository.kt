package ru.renett.newapp.domain.repository

import ru.renett.newapp.domain.models.Coordinates
import ru.renett.newapp.domain.models.CitySimpleWeather
import ru.renett.newapp.domain.models.CityDetailedWeather

interface WeatherRepository {
	suspend fun getWeatherInNearCities(coordinates: Coordinates, cityCount: Int) : MutableList<CitySimpleWeather>
	suspend fun getWeatherInCityByName(city: String) : CityDetailedWeather
	suspend fun getWeatherInCityById(id: Int) : CityDetailedWeather
	fun getWeatherIconURL(iconTitle: String) : String
}
