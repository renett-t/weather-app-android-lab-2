package ru.renett.newapp.presentation.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import ru.renett.newapp.R
import ru.renett.newapp.databinding.FragmentMainBinding
import ru.renett.newapp.domain.usecases.location.GetLocationUseCase
import ru.renett.newapp.domain.usecases.weather.GetSimpleWeatherForCitiesUseCase
import ru.renett.newapp.data.mapper.CityWeatherMapperImpl
import ru.renett.newapp.data.repositories.LocationRepositoryImpl
import ru.renett.newapp.data.repositories.WeatherRepositoryImpl
import ru.renett.newapp.domain.models.Coordinates
import ru.renett.newapp.domain.usecases.weather.*
import ru.renett.newapp.presentation.MainActivity
import ru.renett.newapp.presentation.rv.CityAdapter

class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding

    private lateinit var getCitiesWeather: GetSimpleWeatherForCitiesUseCase
    private lateinit var getWeatherIcon: GetWeatherIconUseCase
    private lateinit var getCityWeatherByName: GetWeatherByNameUseCase

    private lateinit var getLocation: GetLocationUseCase
    private lateinit var coordinates: Coordinates
    private lateinit var cityAdapter: CityAdapter
    private val cityCount = 10

    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { _ ->
            getLocation()
            updateRecyclerView()
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        (activity as MainActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
        }
        initializeServices()
        initSearchBar()
    }

    private fun initSearchBar() {
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                showMessage("Wait! We're getting weather info for u! <3")
                checkInputAndGetCity(query)
                return false
            }
        })
    }

    private fun checkInputAndGetCity(newText: String) {
        scope.launch {
            withContext(Dispatchers.IO) {
                val cityWeatherData = getCityWeatherByName(newText)

                if (cityWeatherData != null) {
                    withContext(Dispatchers.Main) {
                        navigateToFragment(cityWeatherData.id)
                    }
                } else {
                    showMessage("No weather info about '${newText}' found")
                }
            }
        }
    }

    private fun initializeServices() {                 // instead of this should be di-container & ui-delegates
        getLocation = GetLocationUseCase(LocationRepositoryImpl(requireContext()))
        val weatherRepository = WeatherRepositoryImpl(CityWeatherMapperImpl())
        getCitiesWeather = GetSimpleWeatherForCitiesUseCase(weatherRepository)
        getWeatherIcon = GetWeatherIconUseCase(weatherRepository)
        getCityWeatherByName = GetWeatherByNameUseCase(weatherRepository)

        cityAdapter = CityAdapter { navigateToFragment(it) }

        binding.rvCities.apply {
            adapter = cityAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }
    }

    override fun onResume() {
        super.onResume()
        if (!checkPermissionsGranted())
            requestLocationAccess()
        coordinates = getLocation()
        updateRecyclerView()
    }

    private fun requestLocationAccess() {
        locationPermissionLauncher.launch(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
        )
    }

    private fun checkPermissionsGranted(): Boolean {
        activity?.run {
            return (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)
        }

        return false
    }

    private fun navigateToFragment(id: Int) {
        val bundle = Bundle().apply {
            putInt(CITY_ID, id)
        }

        val options = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(R.anim.enter_from_right)
            .setExitAnim(R.anim.fade_out)
            .setPopEnterAnim(R.anim.fade_in)
            .setPopExitAnim(R.anim.exit_to_right)
            .build()

        findNavController().navigate(
            R.id.action_mainFragment_to_weatherDetailsFragment2,
            bundle,
            options
        )
    }

    private fun showMessage(message: String) {
        Snackbar.make(
            requireActivity().findViewById(R.id.fragment_container),
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun updateRecyclerView() {
        scope.launch {
            withContext(Dispatchers.IO) {
                val list = getCitiesWeather(coordinates, cityCount)
                withContext(Dispatchers.Main) {
                    if (list.isEmpty()) {
                        showMessage("Problem with retrieving weather in near cities.")
                    }

                    for (item in list) {
                        item.icon = getWeatherIcon(item.icon)
                    }
                    cityAdapter.submitList(list)
                }
            }
        }
    }
}
