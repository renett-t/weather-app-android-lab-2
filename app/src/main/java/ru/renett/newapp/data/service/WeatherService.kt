package ru.renett.newapp.data.service

import ru.renett.newapp.data.api.OpenWeatherApi
import ru.renett.newapp.data.mapper.CityWeatherMapper
import ru.renett.newapp.domain.models.CityDetailedWeather
import ru.renett.newapp.domain.repository.WeatherRepository
import ru.renett.newapp.domain.models.Coordinates
import ru.renett.newapp.domain.models.CitySimpleWeather

class WeatherService (
    private val api: OpenWeatherApi,
    private val mapper: CityWeatherMapper,
) : WeatherRepository {

    override suspend fun getWeatherInNearCities(
        coordinates: Coordinates,
        cityCount: Int
    ): MutableList<CitySimpleWeather> {
        val cities = ArrayList<CitySimpleWeather>()
        val citiesRaw = api.getWeatherInNearCities(coordinates.lat, coordinates.lon, cityCount)
        if (citiesRaw != null) {
            for (cityWeatherData in citiesRaw.listOfCitiesWeather) {
                cities.add(mapper.mapToCitySimpleWeather(cityWeatherData))
            }
        }

        return cities
    }

    override suspend fun getWeatherInCityByName(city: String): CityDetailedWeather {
        return mapper.mapToCityDetailedWeather(api.getWeatherInCityByName(city))
    }

    override suspend fun getWeatherInCityById(id: Int): CityDetailedWeather {
        return mapper.mapToCityDetailedWeather(api.getWeatherInCityById(id))
    }

    override fun getWeatherIconURL(iconTitle: String): String {
        return "https://openweathermap.org/img/wn/${iconTitle}@4x.png"
    }
}
