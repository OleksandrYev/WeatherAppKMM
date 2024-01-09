package network

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import entity.weather.WeatherEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.weatherapp.BuildConfig

class WeatherApi(private val httpClient: HttpClient) {

    @NativeCoroutines
    suspend fun getWeather(lat: Double, long: Double): WeatherEntity =
        httpClient.get("${BuildConfig.WEATHER_BASE_API}$GET_WEATHER_ENDPOINT?lat=${lat}&lon=${long}" +
                "&appid=${BuildConfig.WEATHER_API_KEY}&units=metric")
            .body()

    private companion object {
        const val GET_WEATHER_ENDPOINT = "/weather"
    }
}