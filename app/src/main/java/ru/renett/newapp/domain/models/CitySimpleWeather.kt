package ru.renett.newapp.domain.models

data class CitySimpleWeather(
    val id: Int,
    val name: String,
    val temperature: Double,
    var icon: String,
)
