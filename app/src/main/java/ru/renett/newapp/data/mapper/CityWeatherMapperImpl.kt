package ru.renett.newapp.data.mapper

import ru.renett.newapp.data.response.CityWeatherData
import ru.renett.newapp.data.response.CityWeatherResponse
import ru.renett.newapp.domain.models.CityDetailedWeather
import ru.renett.newapp.domain.models.CitySimpleWeather
import javax.inject.Inject

class CityWeatherMapperImpl @Inject constructor() : CityWeatherMapper {

    override fun mapToCitySimpleWeather(response: CityWeatherData) = CitySimpleWeather(
        response.id,
        response.cityTitle,
        response.weatherInfo.temperature,
        response.weatherState[0].icon
    )

    override fun mapToCityDetailedWeather(response: CityWeatherResponse) = CityDetailedWeather(
        response.id,
        response.cityTitle,
        response.weatherInfo.temperature,
        response.weatherState[0].icon,
        response.weatherInfo.feelsLike,
        response.weatherState[0].description,
        response.weatherInfo.pressure,
        response.weatherInfo.humidity,
        response.wind.speed,
        response.wind.degree
    )
}
