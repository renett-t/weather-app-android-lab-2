package ru.renett.newapp.presentation.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import ru.renett.newapp.R
import ru.renett.newapp.data.mapper.CityWeatherMapperImpl
import ru.renett.newapp.data.repositories.LocationRepositoryImpl
import ru.renett.newapp.data.repositories.WeatherRepositoryImpl
import ru.renett.newapp.databinding.FragmentWeatherDetailsBinding
import ru.renett.newapp.domain.converters.DateConverter
import ru.renett.newapp.domain.converters.WindDirectionConverter
import ru.renett.newapp.domain.models.CityDetailedWeather
import ru.renett.newapp.domain.usecases.location.GetLocationUseCase
import ru.renett.newapp.domain.usecases.weather.GetSimpleWeatherForCitiesUseCase
import ru.renett.newapp.domain.usecases.weather.GetWeatherByIdUseCase
import ru.renett.newapp.domain.usecases.weather.GetWeatherByNameUseCase
import ru.renett.newapp.domain.usecases.weather.GetWeatherIconUseCase
import ru.renett.newapp.presentation.MainActivity
import ru.renett.newapp.presentation.viewmodels.ViewModelFactory
import ru.renett.newapp.presentation.viewmodels.WeatherDetailsViewModel
import java.util.*
import java.util.Locale.ENGLISH

const val CITY_ID = "city_id"
class WeatherDetailsFragment : Fragment(R.layout.fragment_weather_details) {
    private lateinit var viewModel : WeatherDetailsViewModel

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
        initializeServices()
        initializeObservers()

        val cityId = arguments?.get(CITY_ID)
        viewModel.requestWeatherInCity(cityId as Int)
    }

    private fun initializeObservers() {
        viewModel.weatherDetails.observe(viewLifecycleOwner) { result ->
            result.fold(onSuccess = { cityWeather ->
                initializeView(cityWeather)
            }, onFailure = {
                showMessage("Sorry, unable to get information.")
            })
        }
    }

    private fun initializeServices() {
        val weatherRepository = WeatherRepositoryImpl(CityWeatherMapperImpl())
        val factory = ViewModelFactory(
            GetSimpleWeatherForCitiesUseCase(weatherRepository),
            GetWeatherByNameUseCase(weatherRepository),
            GetLocationUseCase(LocationRepositoryImpl(requireContext())),
            GetWeatherByIdUseCase(weatherRepository),
            GetWeatherIconUseCase(weatherRepository)
        )
        viewModel = ViewModelProvider(this, factory)[WeatherDetailsViewModel::class.java]
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

    private fun initializeView(city: CityDetailedWeather) {
        with(binding) {
            tvCity.text = city.name
            tvDate.text = dateConverter.convertDateToPrettyString(GregorianCalendar.getInstance(ENGLISH))
            tvTemperature.text = "${city.temperature}°"
            tvWeatherState.text = city.weatherState

            Glide.with(requireContext()).load(city.icon)
                .centerCrop()
                .into(ivWeatherIcon)

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
