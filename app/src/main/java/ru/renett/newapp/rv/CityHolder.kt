package ru.renett.newapp.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.renett.newapp.databinding.ItemCityBinding
import ru.renett.newapp.models.City

class CityHolder (
    private val binding: ItemCityBinding,
    private val onItemChosenAction: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var city: City? = null

    init {
        itemView.setOnClickListener {
            city?.let {
                onItemChosenAction(it.id)
            }
        }
    }

    fun bind(city: City): Unit {
        this.city = city
        with(binding) {
            tvCity.text = city.name
            tvTemperature.text = city.temperature.toString()
        }
    }

    companion object {
        fun create(parent: ViewGroup, onItemChosenAction: (Int) -> Unit) : CityHolder {
            return CityHolder(ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false), onItemChosenAction)
        }
    }
}
