package ru.renett.newapp.data.mapper

import ru.renett.newapp.data.response.CityWeatherData
import ru.renett.newapp.data.response.CityWeatherResponse
import ru.renett.newapp.domain.models.CityDetailedWeather
import ru.renett.newapp.domain.models.CitySimpleWeather

interface CityWeatherMapper {
    fun mapToCitySimpleWeather(response: CityWeatherData) : CitySimpleWeather
    fun mapToCityDetailedWeather(response: CityWeatherResponse) : CityDetailedWeather
}
