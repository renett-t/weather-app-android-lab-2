package ru.renett.newapp.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.renett.newapp.data.repositories.WeatherRepositoryImpl
import ru.renett.newapp.domain.repository.WeatherRepository

@Module
@InstallIn(SingletonComponent::class)
interface RepoModule {

    @Binds
    fun provideWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl) : WeatherRepository
}
