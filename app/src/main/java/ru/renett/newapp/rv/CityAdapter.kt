package ru.renett.newapp.rv

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.renett.newapp.models.City

class CityAdapter (
    private val onItemChosenAction: (Int) -> Unit
) : ListAdapter<City, CityHolder>(CityDiffUtilItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder =
         CityHolder.create(parent, onItemChosenAction)

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: MutableList<City>?) {
        super.submitList(if (list == null) null else ArrayList(list))
    }
}

class CityDiffUtilItemCallback : DiffUtil.ItemCallback<City>() {
    override fun areItemsTheSame(oldItem: City, newItem: City): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: City, newItem: City): Boolean = oldItem == newItem
}
