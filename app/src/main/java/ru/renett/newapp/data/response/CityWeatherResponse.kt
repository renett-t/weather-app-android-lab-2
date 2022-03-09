package ru.renett.newapp.data.response

import com.google.gson.annotations.SerializedName

data class CityWeatherResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val cityTitle: String,
    @SerializedName("cod")
    val codeStatus: Int,
    @SerializedName("coord")
    val coordinates: Coordinates,
    @SerializedName("main")
    val weatherInfo: WeatherInfo,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("weather")
    val weatherState: List<WeatherState>,
    @SerializedName("wind")
    val wind: Wind
)
