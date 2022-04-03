package ru.renett.newapp.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.renett.newapp.di.modules.adapter.AdapterModule
import ru.renett.newapp.di.modules.LocationModule
import ru.renett.newapp.di.modules.NetModule
import ru.renett.newapp.di.modules.RepoModule
import ru.renett.newapp.di.modules.viewmodel.ViewModelModule

@Module(includes = [
    NetModule::class,
    RepoModule::class,
    ViewModelModule::class,
    LocationModule::class,
    AdapterModule::class
])
class AppModule {

    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}
