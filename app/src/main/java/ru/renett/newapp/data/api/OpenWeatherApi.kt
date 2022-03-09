package ru.renett.newapp.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.renett.newapp.data.response.CitiesWeatherResponse
import ru.renett.newapp.data.response.CityWeatherResponse

interface OpenWeatherApi {

    @GET("data/2.5/find")
    suspend fun getWeatherInNearCities(@Query("lat") latitude: Double,
                                       @Query("lon") longitude: Double,
                                       @Query("cnt") count: Int) : CitiesWeatherResponse

    @GET("data/2.5/weather")
    suspend fun getWeatherInCityByName(@Query("q") city: String) : CityWeatherResponse

    @GET("data/2.5/weather")
    suspend fun getWeatherInCityById(@Query("id") cityId: Int) : CityWeatherResponse

    fun getWeatherIconURL(iconTitle: String) : String
}
