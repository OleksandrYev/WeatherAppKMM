package org.weatherapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import entity.weather.LocationEntity
import entity.weather.WeatherEntity
import entity.weather.celsiusToFahrenheit
import repository.RemoteRepository

private const val WEATHER_CARD_PADDING_DP = 8
private const val WEATHER_CARD_ELEVATION_DP = 8
private const val WEATHER_CONTENT_PADDING_DP = 8
private const val WEATHER_ICON_SIZE_DP = 128

@Composable
fun WeatherScreen(cityName: String, location: LocationEntity) {
    var weather: WeatherEntity? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        weather = RemoteRepository().getWeather(location)
    }

    weather?.let {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(WEATHER_CARD_PADDING_DP.dp),
            elevation = WEATHER_CARD_ELEVATION_DP.dp,
        ) {
            Column(modifier = Modifier.padding(WEATHER_CONTENT_PADDING_DP.dp)) {
                Text(
                    modifier = Modifier.align(CenterHorizontally),
                    text = cityName,
                    style = MaterialTheme.typography.h4
                )
                AsyncImage(
                    modifier = Modifier
                        .size(WEATHER_ICON_SIZE_DP.dp)
                        .align(CenterHorizontally),
                    model = it.weather[0].iconUrl,
                    contentDescription = it.weather[0].description,
                    contentScale = ContentScale.Fit
                )
                Text(
                    "Feels like: ${it.main.feelsLike} 'C / ${celsiusToFahrenheit(it.main.feelsLike)} 'F",
                    style = MaterialTheme.typography.body1
                )
                Text(
                    "Temperature: ${it.main.temperature} 'C / ${celsiusToFahrenheit(it.main.temperature)} 'F",
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}