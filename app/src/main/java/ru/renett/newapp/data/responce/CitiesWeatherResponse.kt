package ru.renett.newapp.data.responce

data class CitiesWeatherResponse(
    val cod: String,
    val count: Int,
    val list: List<CityWeather>,
    val message: String
)
