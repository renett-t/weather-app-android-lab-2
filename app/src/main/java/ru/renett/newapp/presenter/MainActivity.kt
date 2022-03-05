package ru.renett.newapp.presenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.renett.newapp.R

const val CITY_ID = "city_id"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
