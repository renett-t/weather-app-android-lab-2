package ru.renett.newapp.domain.converters

import javax.inject.Inject

class WindDirectionConverter @Inject constructor()  {

    fun convertDegreeToDirection(degree: Double): String {
        return when (degree) {
            in 0.0..22.5 -> Direction.NORTH.direction
            in 22.5..67.5 -> Direction.NORTHEAST.direction
            in 67.5..112.5 -> Direction.EAST.direction
            in 112.5..157.5 -> Direction.SOUTHEAST.direction
            in 157.5..202.5 -> Direction.SOUTH.direction
            in 202.5..247.5 -> Direction.SOUTHWEST.direction
            in 247.5..292.5 -> Direction.WEST.direction
            in 292.5..337.5 -> Direction.NORTHWEST.direction
            else -> Direction.NORTH.direction
        }
    }
}

enum class Direction(val direction: String) {
    NORTH("n"),
    NORTHEAST("ne"),
    EAST("e"),
    SOUTHEAST("se"),
    SOUTH("s"),
    SOUTHWEST("sw"),
    WEST("w"),
    NORTHWEST("nw")
}
