package ru.renett.newapp.ui

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import ru.renett.newapp.CITY_ID
import ru.renett.newapp.R
import ru.renett.newapp.data.responce.Coordinates
import ru.renett.newapp.databinding.FragmentMainBinding
import ru.renett.newapp.rv.CityAdapter
import ru.renett.newapp.service.LocationService
import ru.renett.newapp.service.WeatherService

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var weatherService: WeatherService
    private lateinit var locationService: LocationService
    private lateinit var coordinates: Coordinates
    private val cityCount = 10

    private lateinit var cityAdapter: CityAdapter

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true || permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                Log.e("PERMISSION", "GRANTED LOCATION")
                coordinates = locationService.getCoordinates()
            } else {
                coordinates = locationService.getDefaultCoordinates()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.bind(inflater.inflate(R.layout.fragment_main, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationService = LocationService(context?.applicationContext)
        requestLocationAccess()

        cityAdapter = CityAdapter { navigateToFragment(it) }

        binding.rvCities.apply{
            adapter = cityAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }

//        binding.searchBar.setOnQueryTextListener()

        cityAdapter.submitList(weatherService.getWeatherInNearCities(coordinates, cityCount))
    }


    fun requestLocationAccess() {
        locationPermissionLauncher.launch(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
        )
    }

    private fun navigateToFragment(id: Int) {
        val bundle = Bundle().apply {
            putInt(CITY_ID, id)
        }

        val options = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .build()

        findNavController().navigate(
            R.id.action_mainFragment_to_weatherDetailsFragment2,
            bundle,
            options
        )
    }
}
