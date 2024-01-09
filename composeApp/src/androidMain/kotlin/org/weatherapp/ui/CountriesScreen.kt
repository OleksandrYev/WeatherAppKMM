package org.weatherapp.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import entity.country.CountryEntity
import repository.RemoteRepository

private const val COUNTRIES_CONTENT_PADDING_DP = 8

@Composable
fun CountriesScreen(navigateToWeather: (String, Double, Double) -> Unit) {
    var listCountries: List<CountryEntity> by remember { mutableStateOf(mutableListOf()) }

    LaunchedEffect(Unit) {
        listCountries = RemoteRepository().getCountries()
    }

    LazyColumn(
        contentPadding = PaddingValues(COUNTRIES_CONTENT_PADDING_DP.dp)
    ) {
        itemsIndexed(items = listCountries) { index, item ->
            CountryCard(
                modifier = Modifier,
                country = item,
                currentCountry = index == 0,
                navigateToWeather = navigateToWeather
            )
        }
    }
}