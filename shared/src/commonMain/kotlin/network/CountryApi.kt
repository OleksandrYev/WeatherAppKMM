package network

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import entity.country.CountryEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.weatherapp.BuildConfig

class CountryApi(private val httpClient: HttpClient) {

    @NativeCoroutines
    suspend fun getAllCountries(): List<CountryEntity> {
        return httpClient.get("${BuildConfig.COUNTRY_BASE_API}$GET_ALL_COUNTRIES_ENDPOINT")
            .body<List<CountryEntity>>()
            .sortedBy { it.name.common }
    }

    private companion object {
        const val GET_ALL_COUNTRIES_ENDPOINT = "/all"
    }
}