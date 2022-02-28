package ru.renett.newapp.data.responce

data class CityWeatherResponse(
    val id: Int,
    val name: String,
    val cod: Int,
    val dt: Int,
    val coord: Coord,
    val main: WeatherInfo,
    val visibility: Int,
    val weather: List<WeatherState>,
    val wind: Wind
)
