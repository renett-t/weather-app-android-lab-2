package ru.renett.newapp.di.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.renett.newapp.BuildConfig
import ru.renett.newapp.data.api.OpenWeatherApi
import ru.renett.newapp.data.mapper.CityWeatherMapper
import ru.renett.newapp.data.mapper.CityWeatherMapperImpl
import javax.inject.Qualifier

private const val BASE_URL = "https://api.openweathermap.org/"
private const val API_KEY = "c1c30fed56d3fe139b4e672c13e1bfac"
private const val API_KEY_QUERY = "appid"
private const val UNITS_VALUE = "metric"
private const val UNITS_QUERY = "units"

@Module(includes = [NetBindModule::class])
class NetModule {

    @Provides
    fun provideOpenWeatherApi(okHttpClient: OkHttpClient) : OpenWeatherApi {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherApi::class.java)

    }

    @Provides
    fun provideOkHttpClient(@ApiInterceptor apiKeyInterceptor: Interceptor, @UnitsInterceptor unitsInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor)
                .addInterceptor(unitsInterceptor)
                .also {
                    if (BuildConfig.DEBUG) {
                        it.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    }
                }
                .build()
    }

    @Provides
    @UnitsInterceptor
    fun provideUnitsInterceptor(): Interceptor {
        return Interceptor { chain ->
            createNewInterceptor(chain, UNITS_QUERY, UNITS_VALUE)
        }
    }

    @Provides
    @ApiInterceptor
    fun provideApiKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            createNewInterceptor(chain, API_KEY_QUERY, API_KEY)

        }
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
}

@Module
interface NetBindModule {

    @Binds
    fun provideCityWeatherMapper(cityWeatherMapperImpl: CityWeatherMapperImpl) : CityWeatherMapper
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiInterceptor

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class UnitsInterceptor
