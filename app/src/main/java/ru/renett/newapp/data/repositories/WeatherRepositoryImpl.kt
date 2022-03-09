package ru.renett.newapp.data.repositories

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.renett.newapp.BuildConfig
import ru.renett.newapp.data.api.OpenWeatherApi
import ru.renett.newapp.data.mapper.CityWeatherMapper
import ru.renett.newapp.domain.models.CityDetailedWeather
import ru.renett.newapp.domain.models.CitySimpleWeather
import ru.renett.newapp.domain.models.Coordinates
import ru.renett.newapp.domain.repository.RepositoryException
import ru.renett.newapp.domain.repository.WeatherRepository

private const val BASE_URL = "https://api.openweathermap.org/"
private const val API_KEY = "c1c30fed56d3fe139b4e672c13e1bfac"
private const val API_KEY_QUERY = "appid"
private const val UNITS_VALUE = "metric"
private const val UNITS_QUERY = "units"

class WeatherRepositoryImpl(
    private val mapper: CityWeatherMapper,
) : WeatherRepository {

    private val apiKeyInterceptor = Interceptor { chain ->
        createNewInterceptor(chain, API_KEY_QUERY, API_KEY)

    }

    private val unitsInterceptor = Interceptor { chain ->
        createNewInterceptor(chain, UNITS_QUERY, UNITS_VALUE)
    }

    private fun createNewInterceptor(
        chain: Interceptor.Chain,
        query: String,
        value: String
    ): Response {
        val original = chain.request()

        val newUrl = original.url.newBuilder()
            .addQueryParameter(query, value)
            .build()

        return chain.proceed(
            original.newBuilder()
                .url(newUrl)
                .build()
        )
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(unitsInterceptor)
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                }
            }
            .build()
    }

    private val api: OpenWeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenWeatherApi::class.java)
    }

    override suspend fun getWeatherInNearCities(
        coordinates: Coordinates,
        cityCount: Int
    ): MutableList<CitySimpleWeather> {
        try {
            val cities = ArrayList<CitySimpleWeather>()
            val citiesRaw = api.getWeatherInNearCities(coordinates.lat, coordinates.lon, cityCount)
            if (citiesRaw != null) {
                for (cityWeatherData in citiesRaw.listOfCitiesWeather) {
                    cities.add(mapper.mapToCitySimpleWeather(cityWeatherData))
                }
            }

            return cities
        } catch (e: HttpException) {
            throw RepositoryException(e)
        }
    }

    override suspend fun getWeatherInCityByName(city: String): CityDetailedWeather? {
        try {
            val resp = api.getWeatherInCityByName(city);
            return if (resp == null) null else mapper.mapToCityDetailedWeather(resp)
        } catch (e: HttpException) {
            throw RepositoryException(e)
        }
    }

    override suspend fun getWeatherInCityById(id: Int): CityDetailedWeather? {
        try {
            val resp = api.getWeatherInCityById(id);
            return if (resp == null) null else mapper.mapToCityDetailedWeather(resp)
        } catch (e: HttpException) {
            throw RepositoryException(e)
        }
    }

    override fun getWeatherIconURL(iconTitle: String): String {
        return "https://openweathermap.org/img/wn/${iconTitle}@4x.png"
    }
}
