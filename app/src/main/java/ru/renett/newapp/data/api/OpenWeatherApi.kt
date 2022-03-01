package ru.renett.newapp.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.renett.newapp.data.responce.CitiesWeatherData
import ru.renett.newapp.data.responce.CityWeatherData

interface OpenWeatherApi {

    @GET("data/2.5/find")
    suspend fun getWeatherInNearCities(@Query("lat") latitude: Double,
                                       @Query("lon") longitude: Double,
                                       @Query("cnt") count: Int) : CitiesWeatherData

    @GET("data/2.5/weather")
    suspend fun getWeatherInCity(@Query("q") city: String) : CityWeatherData

    @GET("data/2.5/weather")
    suspend fun getWeatherInCityById(@Query("id") cityId: Int) : CityWeatherData

//    @GET("img/wn/{iconTitle}")
//    suspend fun getIconByName(@Path("iconTitle") iconTitle: String) : File
//      https://openweathermap.org/img/wn/10d@2x.png


}
