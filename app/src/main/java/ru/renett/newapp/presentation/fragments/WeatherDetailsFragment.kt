package ru.renett.newapp.presentation.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ru.renett.newapp.R
import ru.renett.newapp.databinding.FragmentWeatherDetailsBinding
import ru.renett.newapp.domain.converters.DateConverter
import ru.renett.newapp.domain.converters.WindDirectionConverter
import ru.renett.newapp.domain.models.CityDetailedWeather
import ru.renett.newapp.presentation.MainActivity
import ru.renett.newapp.presentation.viewmodels.WeatherDetailsViewModel
import java.util.*
import java.util.Locale.ENGLISH
import javax.inject.Inject

const val CITY_ID = "city_id"
@AndroidEntryPoint
class WeatherDetailsFragment : Fragment(R.layout.fragment_weather_details) {
    @Inject
    lateinit var windConverter: WindDirectionConverter

    @Inject
    lateinit var dateConverter: DateConverter

    private val viewModel : WeatherDetailsViewModel by viewModels()

    private lateinit var binding: FragmentWeatherDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherDetailsBinding.bind(view)

        setHasOptionsMenu(true)
        (activity as MainActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

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
