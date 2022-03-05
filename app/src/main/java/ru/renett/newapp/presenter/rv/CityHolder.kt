package ru.renett.newapp.presenter.rv

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.renett.newapp.databinding.ItemCityBinding
import ru.renett.newapp.domain.models.CitySimpleWeather

class CityHolder (
    private val binding: ItemCityBinding,
    private val onItemChosenAction: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var city: CitySimpleWeather? = null

    init {
        itemView.setOnClickListener {
            city?.let {
                onItemChosenAction(it.id)
            }
        }
    }

    fun bind(city: CitySimpleWeather): Unit {
        this.city = city
        with(binding) {
            tvCity.text = city.name
            tvTemperature.setTextColor(getColorDependingOnDegree(city.temperature))
            tvTemperature.text = city.temperature.toString()
            Glide.with(itemView).load(city.icon)
                .centerCrop()
                .into(ivWeatherIcon)
        }
    }

    private fun getColorDependingOnDegree(temperature: Double): Int {
        val result : DegreeColor = when (temperature) {
            in -20.0..-10.0 -> DegreeColor.BLUE
            in -10.0..0.0 -> DegreeColor.SEA
            in 0.0..10.0 -> DegreeColor.YELLOW
            in 10.0..20.0 -> DegreeColor.ORANGE
            in 20.0..Double.MAX_VALUE -> DegreeColor.RED
            else -> DegreeColor.DARK_BLUE
        }

        return Color.parseColor(result.hex)
    }

    companion object {
        fun create(parent: ViewGroup, onItemChosenAction: (Int) -> Unit) : CityHolder {
            return CityHolder(ItemCityBinding.inflate(
                LayoutInflater.from(parent.context), parent, false),
                onItemChosenAction)
        }
    }

}

enum class DegreeColor(val hex: String) {
    DARK_BLUE("#0057F1"),
    BLUE("#009FFE"),
    SEA("#00BCDD"),
    YELLOW("#F9F871"),
    ORANGE("#FF7347"),
    RED("#FF0069")
}
