package ru.renett.newapp.di.modules.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Binds
import dagger.multibindings.IntoMap
import ru.renett.newapp.presentation.viewmodels.MainViewModel
import ru.renett.newapp.presentation.viewmodels.WeatherDetailsViewModel

@Module
interface ViewModelModule {

//    @Binds
//    fun bindViewModelFactory(
//        factory: ViewModelFactory
//    ): ViewModelProvider.Factory

    @Binds
    @[IntoMap ViewModelKey(MainViewModel::class)]
    fun provideMainViewModel(vm: MainViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(WeatherDetailsViewModel::class)]
    fun provideWeatherDetailsViewModel(vm: WeatherDetailsViewModel): ViewModel

}
