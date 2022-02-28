package ru.renett.newapp.data.responce

import com.google.gson.annotations.SerializedName

data class WeatherState(
    val id: Int,
    @SerializedName("main")
    val stateTitle: String,
    val description: String,
    val icon: String,
)
