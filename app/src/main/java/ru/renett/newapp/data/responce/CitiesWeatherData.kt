package ru.renett.newapp.data.responce

import com.google.gson.annotations.SerializedName

data class CitiesWeatherData(
    @SerializedName("cod")
    val codeStatus: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("list")
    val listOfCitiesWeather: List<CityWeather>,
    @SerializedName("message")
    val message: String
)
