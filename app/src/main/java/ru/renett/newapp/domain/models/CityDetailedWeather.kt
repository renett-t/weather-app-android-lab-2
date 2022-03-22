package ru.renett.newapp.domain.models

data class CityDetailedWeather (
    val id: Int,
    val name: String,
    val temperature: Double,
    var icon: String,
    val feelsLike: Double,
    val weatherState: String,
    val pressure: Int,
    val humidity: Int,
    val windSpeed: Double,
    val windDegree: Double,
)
