package ru.renett.newapp.data.responce

import com.google.gson.annotations.SerializedName

data class CityWeather(
    val id: Int,
    @SerializedName("name")
    val cityTitle: String,
    val coord: Coord,
    @SerializedName("main")
    val weatherInfo: WeatherInfo,
    val dt: Int,
    val wind: Wind,
    val weather: List<WeatherState>,
)
