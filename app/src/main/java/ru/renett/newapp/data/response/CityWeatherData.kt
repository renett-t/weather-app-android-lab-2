package ru.renett.newapp.data.response

import com.google.gson.annotations.SerializedName

data class CityWeatherData(
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
