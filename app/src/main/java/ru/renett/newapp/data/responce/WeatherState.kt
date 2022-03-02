package ru.renett.newapp.data.responce

import com.google.gson.annotations.SerializedName

data class WeatherState(
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val stateTitle: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String,
)
