package ru.renett.newapp.data.responce

import com.google.gson.annotations.SerializedName

data class CityWeather(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val cityTitle: String,
    @SerializedName("coord")
    val coordinates: Coordinates,
    @SerializedName("main")
    val weatherInfo: WeatherInfo,
    @SerializedName("wind")
    val wind: Wind,
    @SerializedName("weather")
    val weatherState: List<WeatherState>,
)
