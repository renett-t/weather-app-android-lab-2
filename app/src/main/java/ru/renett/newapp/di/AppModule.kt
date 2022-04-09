package ru.renett.newapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.renett.newapp.di.modules.LocationModule
import ru.renett.newapp.di.modules.NetModule
import ru.renett.newapp.di.modules.RepoModule

@Module(includes = [
    NetModule::class,
    RepoModule::class,
    LocationModule::class,
])
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

//    @Provides
//    fun providesContext(): Context = null
}
