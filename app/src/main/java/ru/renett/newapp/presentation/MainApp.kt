package ru.renett.newapp.presentation

import android.app.Application
import ru.renett.newapp.di.AppComponent
import ru.renett.newapp.di.DaggerAppComponent

class MainApp : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
    }
}
