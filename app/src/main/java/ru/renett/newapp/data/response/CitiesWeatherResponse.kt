package ru.renett.newapp.data.response

import com.google.gson.annotations.SerializedName

data class CitiesWeatherResponse(
    @SerializedName("cod")
    val codeStatus: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("list")
    val listOfCitiesWeather: List<CityWeatherData>,
    @SerializedName("message")
    val message: String
)
