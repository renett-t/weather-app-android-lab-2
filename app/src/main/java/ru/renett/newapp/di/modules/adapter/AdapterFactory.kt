package ru.renett.newapp.di.modules.adapter

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import ru.renett.newapp.presentation.rv.CityAdapter

@AssistedFactory
interface AdapterFactory {
    fun create(@Assisted("onItemChosenAction") onItemChosenAction: (Int) -> Unit): CityAdapter
}
