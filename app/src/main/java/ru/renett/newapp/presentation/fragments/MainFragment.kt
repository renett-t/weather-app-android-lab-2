package ru.renett.newapp.presentation.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
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
import ru.renett.newapp.presentation.viewmodels.MainViewModel
import ru.renett.newapp.presentation.viewmodels.ViewModelFactory

class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding

    private lateinit var viewModel: MainViewModel

    private lateinit var coordinates: Coordinates
    private lateinit var cityAdapter: CityAdapter
    private val cityCount = 10

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { _ ->
            viewModel.requestLocation()
            viewModel.requestCitiesWeather(coordinates, cityCount)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        (activity as MainActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
        }

        initializeServices()
        initObservers()
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
        viewModel.requestCityWeatherByName(newText)
    }

    private fun initObservers() {
        viewModel.location.observe(viewLifecycleOwner) {
            it.fold(onSuccess = { coordinatesResult ->
                coordinates = coordinatesResult
            }, onFailure = {
                Log.e("Location", "Unable to get location")
            })
        }
        viewModel.citiesWeather.observe(viewLifecycleOwner) {
            it.fold(onSuccess = { list ->
                cityAdapter.submitList(list)
            }, onFailure = {
                showMessage("Problem with retrieving weather in near cities.")
            })
        }
        viewModel.cityWeather.observe(viewLifecycleOwner) {
            if (it != null) {
                it.fold(onSuccess = { cityWeather ->
                    navigateToFragment(cityWeather.id)
                }, onFailure = { ex ->
                    ex.message?.let { it1 -> showMessage(it1) }
                })
            }
        }
    }

    private fun initializeServices() {                 // instead of this should be di-container
        val weatherRepository = WeatherRepositoryImpl(CityWeatherMapperImpl())
        val factory = ViewModelFactory(
            GetSimpleWeatherForCitiesUseCase(weatherRepository),
            GetWeatherByNameUseCase(weatherRepository),
            GetLocationUseCase(LocationRepositoryImpl(requireContext())),
            GetWeatherByIdUseCase(weatherRepository),
            GetWeatherIconUseCase(weatherRepository)
        )
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

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
        viewModel.requestLocation()
        viewModel.requestCitiesWeather(coordinates, cityCount)
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
            Snackbar.LENGTH_SHORT
        ).show()
    }
}
