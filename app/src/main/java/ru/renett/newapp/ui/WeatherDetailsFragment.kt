package ru.renett.newapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import ru.renett.newapp.CITY_ID
import ru.renett.newapp.R
import ru.renett.newapp.data.WeatherRepository
import ru.renett.newapp.data.responce.CityWeatherData
import ru.renett.newapp.databinding.FragmentWeatherDetailsBinding
import ru.renett.newapp.service.DateConverter
import ru.renett.newapp.service.WeatherService
import ru.renett.newapp.service.WindDirectionConverter
import java.util.*
import java.util.Locale.ENGLISH

class WeatherDetailsFragment : Fragment(R.layout.fragment_weather_details) {
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val weatherService: WeatherService by lazy {
        WeatherService(WeatherRepository)
    }

    private val windConverter: WindDirectionConverter by lazy {
        WindDirectionConverter()
    }

    private val dateConverter: DateConverter by lazy {
        DateConverter()
    }

    private lateinit var binding: FragmentWeatherDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherDetailsBinding.bind(view)
        val cityId = arguments?.get(CITY_ID)

        getWeatherInfoByCityId(cityId as Int)
    }

    private fun getWeatherInfoByCityId(cityId: Int) {
        scope.launch {
            val citiWeather = weatherService.getWeatherInCityById(cityId)
            withContext(Dispatchers.Main) {
                citiWeather?.let {
                    initializeView(it)
                } ?: showMessage("Sorry, unable to get information.")
            }
        }
    }

    private fun initializeView(citiWeather: CityWeatherData) {
        with(binding) {
            tvCity.text = citiWeather.cityTitle
            tvDate.text = dateConverter.convertDateToPrettyString(GregorianCalendar.getInstance(ENGLISH))
            tvTemperature.text = "${citiWeather.weatherInfo.temperature}°"
            tvWeatherState.text = citiWeather.weatherState[0].stateTitle

            scope.launch {
                val url = weatherService.getWeatherIconURL(citiWeather.weatherState[0].icon)
                withContext(Dispatchers.Main) {
                    Glide.with(requireContext()).load(url)
                        .centerCrop()
                        .into(ivWeatherIcon)
                }
            }

            tvFeelsLike.text = "${citiWeather.weatherInfo.feelsLike}°"
            tvPressure.text = "${citiWeather.weatherInfo.pressure} hPa"
            tvHumidity.text = "${citiWeather.weatherInfo.humidity} %"
            tvWindSpeed.text = "${citiWeather.wind.speed} m/s"
            tvWindDirection.text = windConverter.convertDegreeToDirection(citiWeather.wind.degree)
        }
    }

    private fun showMessage(message: String) {
        Snackbar.make(
            requireActivity().findViewById(R.id.fragment_container),
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
