package ru.renett.newapp.di

import android.content.Context
import dagger.BindsInstance
import ru.renett.newapp.presentation.fragments.MainFragment
import ru.renett.newapp.presentation.fragments.WeatherDetailsFragment
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(mainFragment: MainFragment)
    fun inject(weatherDetailsFragment: WeatherDetailsFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context) : Builder
        fun build() : AppComponent
    }
}
