package ru.renett.newapp.presentation.ext

import android.content.Context
import ru.renett.newapp.di.AppComponent
import ru.renett.newapp.presentation.MainApp

val Context.appComponent : AppComponent
    get() = when (this) {
        is MainApp -> appComponent
        else -> this.applicationContext.appComponent
    }
