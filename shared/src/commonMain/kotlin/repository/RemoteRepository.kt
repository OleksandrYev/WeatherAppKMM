package repository

import cache.pathToCountryCache
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import entity.country.CountryEntity
import entity.weather.LocationEntity
import entity.weather.WeatherEntity
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import location.getCountryCodeManager
import network.CountryApi
import network.WeatherApi

class RemoteRepository {
    private val cache: KStore<List<CountryEntity>> = storeOf(filePath = pathToCountryCache())
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    encodeDefaults = true
                    isLenient = true
                    coerceInputValues = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }
    private val countryApi: CountryApi = CountryApi(httpClient)
    private val weatherApi: WeatherApi = WeatherApi(httpClient)

    @NativeCoroutines
    suspend fun getCountries(): List<CountryEntity> {
        val currentCountryCode = getCountryCodeManager().getCountryCode()
        val countries = getSortedCountries().toMutableList()
        val currentCountry = countries.firstOrNull { it.cca2 == currentCountryCode }
        countries.remove(countries.firstOrNull { it.cca2 == currentCountryCode })
        currentCountry?.let { countries.add(0, it) }
        return countries
    }

    @NativeCoroutines
    suspend fun getWeather(location: LocationEntity): WeatherEntity =
        weatherApi.getWeather(location.lat, location.long)

    private suspend fun getSortedCountries(): List<CountryEntity> =
        cache.get() ?: countryApi.getAllCountries().also { cache.set(it) }
}