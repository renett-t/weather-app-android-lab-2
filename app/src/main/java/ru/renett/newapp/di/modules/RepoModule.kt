package ru.renett.newapp.di.modules

import dagger.Binds
import dagger.Module
import ru.renett.newapp.data.repositories.WeatherRepositoryImpl
import ru.renett.newapp.domain.repository.WeatherRepository

@Module
interface RepoModule {

    @Binds
    fun provideWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl) : WeatherRepository
}
