package ru.renett.newapp.presenter.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import ru.renett.newapp.R
import ru.renett.newapp.data.WeatherRepositoryImpl
import ru.renett.newapp.data.mapper.CityWeatherMapperImpl
import ru.renett.newapp.databinding.FragmentWeatherDetailsBinding
import ru.renett.newapp.domain.usecases.DateConverter
import ru.renett.newapp.data.service.WeatherService
import ru.renett.newapp.domain.usecases.WindDirectionConverter
import ru.renett.newapp.domain.models.CityDetailedWeather
import ru.renett.newapp.presenter.MainActivity
import ru.renett.newapp.presenter.CITY_ID
import java.util.*
import java.util.Locale.ENGLISH

class WeatherDetailsFragment : Fragment(R.layout.fragment_weather_details) {
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val weatherService: WeatherService by lazy {
        WeatherService(WeatherRepositoryImpl, CityWeatherMapperImpl())
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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        (activity as MainActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        val cityId = arguments?.get(CITY_ID)
        getWeatherInfoByCityId(cityId as Int)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> navigateToPreviousFragment()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToPreviousFragment(): Boolean {
        findNavController().popBackStack()
        return true
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

    private fun initializeView(city: CityDetailedWeather) {
        with(binding) {
            tvCity.text = city.name
            tvDate.text = dateConverter.convertDateToPrettyString(GregorianCalendar.getInstance(ENGLISH))
            tvTemperature.text = "${city.temperature}°"
            tvWeatherState.text = city.weatherState

            scope.launch {
                val url = weatherService.getWeatherIconURL(city.icon)
                withContext(Dispatchers.Main) {
                    Glide.with(requireContext()).load(url)
                        .centerCrop()
                        .into(ivWeatherIcon)
                }
            }

            tvFeelsLike.text = "${city.feelsLike}°"
            tvPressure.text = "${city.pressure} hPa"
            tvHumidity.text = "${city.humidity} %"
            tvWindSpeed.text = "${city.windSpeed} m/s"
            tvWindDirection.text = windConverter.convertDegreeToDirection(city.windDegree)
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
