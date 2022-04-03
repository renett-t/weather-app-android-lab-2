package ru.renett.newapp.data.repositories

import retrofit2.HttpException
import ru.renett.newapp.data.api.OpenWeatherApi
import ru.renett.newapp.data.mapper.CityWeatherMapper
import ru.renett.newapp.domain.models.CityDetailedWeather
import ru.renett.newapp.domain.models.CitySimpleWeather
import ru.renett.newapp.domain.models.Coordinates
import ru.renett.newapp.domain.repository.RepositoryException
import ru.renett.newapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val mapper: CityWeatherMapper,
    private val api: OpenWeatherApi,
) : WeatherRepository {


    override suspend fun getWeatherInNearCities(
        coordinates: Coordinates,
        cityCount: Int
    ): MutableList<CitySimpleWeather> {
        try {
            val cities = ArrayList<CitySimpleWeather>()
            val citiesRaw = api.getWeatherInNearCities(coordinates.lat, coordinates.lon, cityCount)
            if (citiesRaw != null) {
                for (cityWeatherData in citiesRaw.listOfCitiesWeather) {
                    cities.add(mapper.mapToCitySimpleWeather(cityWeatherData))
                }
            }

            return cities
        } catch (e: HttpException) {
            throw RepositoryException(e)
        }
    }

    override suspend fun getWeatherInCityByName(city: String): CityDetailedWeather? {
        try {
            val resp = api.getWeatherInCityByName(city);
            return if (resp == null) null else mapper.mapToCityDetailedWeather(resp)
        } catch (e: HttpException) {
            throw RepositoryException(e)
        }
    }

    override suspend fun getWeatherInCityById(id: Int): CityDetailedWeather? {
        try {
            val resp = api.getWeatherInCityById(id);
            return if (resp == null) null else mapper.mapToCityDetailedWeather(resp)
        } catch (e: HttpException) {
            throw RepositoryException(e)
        }
    }

    override fun getWeatherIconURL(iconTitle: String): String {
        return "https://openweathermap.org/img/wn/${iconTitle}@4x.png"
    }
}
