package ru.renett.newapp.data.responce

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("deg")
    val degree: Double,
    @SerializedName("speed")
    val speed: Double
)
