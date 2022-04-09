package ru.renett.newapp.di.modules

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.renett.newapp.data.repositories.LocationRepositoryImpl
import ru.renett.newapp.domain.repository.LocationRepository

@Module(includes = [LocationBindModule::class])
@InstallIn(ActivityComponent::class)
class LocationModule {

    @Provides
    fun provideLocationClient(@ApplicationContext context: Context) : FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }
}

@Module
@InstallIn(ActivityComponent::class)
interface LocationBindModule {

    @Binds
    fun provideLocationRepository(locationRepositoryImpl: LocationRepositoryImpl): LocationRepository
}
