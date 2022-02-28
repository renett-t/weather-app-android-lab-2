package ru.renett.newapp.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.renett.newapp.data.responce.CitiesWeatherResponse
import ru.renett.newapp.data.responce.CityWeatherResponse

interface OpenWeatherApi {

    @GET("data/2.5/find")
    suspend fun getWeatherInNearCities(@Query("lat") latitude: Long, @Query("lon") longitude: Long, @Query("cnt") count: Int) : CitiesWeatherResponse

    @GET("data/2.5/weather")
    suspend fun getWeatherInCity(@Query("q") city: String) : CityWeatherResponse

    @GET("data/2.5/weather")
    suspend fun getWeatherInCityById(@Query("id") cityId: Long) : CityWeatherResponse

//    @GET("img/wn/{iconTitle}")
//    suspend fun getIconByName(@Path("iconTitle") iconTitle: String) : File
//      https://openweathermap.org/img/wn/10d@2x.png


}
