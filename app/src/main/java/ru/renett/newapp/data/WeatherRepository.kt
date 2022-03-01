package ru.renett.newapp.data

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.renett.newapp.BuildConfig
import ru.renett.newapp.data.api.OpenWeatherApi
import ru.renett.newapp.data.responce.CitiesWeatherData
import ru.renett.newapp.data.responce.CityWeatherData
import ru.renett.newapp.data.responce.Coordinates

private const val BASE_URL = "https://api.openweathermap.org/"
private const val API_KEY = "c1c30fed56d3fe139b4e672c13e1bfac"
private const val API_KEY_QUERY = "appid"
private const val UNITS_VALUE = "metric"
private const val UNITS_QUERY = "units"

object WeatherRepository {

    private val apiKeyInterceptor = Interceptor { chain ->
        createNewInterceptor(chain, API_KEY_QUERY, API_KEY)

    }

    private val unitsInterceptor = Interceptor { chain ->
        createNewInterceptor(chain, UNITS_QUERY, UNITS_VALUE)
    }

    private fun createNewInterceptor(chain: Interceptor.Chain, query: String, value: String): Response {
        val original = chain.request()

        val newUrl = original.url.newBuilder()
            .addQueryParameter(query, value)
            .build()

        return chain.proceed(original.newBuilder()
            .url(newUrl)
            .build())
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

    // todo - return null|exception on error
    suspend fun getWeatherInNearCities(coordinates: Coordinates, cityCount: Int) : CitiesWeatherData {
        return api.getWeatherInNearCities(coordinates.lat, coordinates.lon, cityCount)
    }

    suspend fun getWeatherInCityByName(city: String) : CityWeatherData {
        return api.getWeatherInCity(city)
    }

    suspend fun getWeatherInCityById(id: Int) : CityWeatherData {
        return api.getWeatherInCityById(id)
    }

    fun getWeatherIconURL(iconTitle: String) : String {
        return "https://openweathermap.org/img/wn/${iconTitle}@4x.png"
    }
}
