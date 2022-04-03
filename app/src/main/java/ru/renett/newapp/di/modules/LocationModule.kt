package ru.renett.newapp.di.modules

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.renett.newapp.data.repositories.LocationRepositoryImpl
import ru.renett.newapp.domain.repository.LocationRepository

@Module(includes = [LocationBindModule::class])
class LocationModule {

    @Provides
    fun provideLocationClient(context: Context) : FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }
}

@Module
interface LocationBindModule {

    @Binds
    fun provideLocationRepository(locationRepositoryImpl: LocationRepositoryImpl): LocationRepository
}
