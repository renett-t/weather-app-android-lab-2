package ru.renett.newapp.data.responce

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("deg")
    val degree: Int,
    @SerializedName("speed")
    val speed: Double
)
