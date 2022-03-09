package ru.renett.newapp.presentation.rv

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.renett.newapp.domain.models.CitySimpleWeather

class CityAdapter (
    private val onItemChosenAction: (Int) -> Unit
) : ListAdapter<CitySimpleWeather, CityHolder>(CityDiffUtilItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder =
         CityHolder.create(parent, onItemChosenAction)

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: MutableList<CitySimpleWeather>?) {
        super.submitList(if (list == null) null else ArrayList(list))
    }
}

class CityDiffUtilItemCallback : DiffUtil.ItemCallback<CitySimpleWeather>() {
    override fun areItemsTheSame(oldItem: CitySimpleWeather, newItem: CitySimpleWeather)
        = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CitySimpleWeather, newItem: CitySimpleWeather)
        = oldItem == newItem
}
