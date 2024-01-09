package org.weatherapp.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import entity.country.CountryEntity

private const val COUNTRY_CARD_PADDING_DP = 4
private const val COUNTRY_CARD_ELEVATION_DP = 4
private const val COUNTRY_BORDER_WIDTH_DP = 4
private const val COUNTRY_CONTENT_PADDING_DP = 8
private const val COUNTRY_FLAG_WIDTH_DP = 130
private const val COUNTRY_FLAG_HEIGHT_DP = 80
private const val COUNTRY_FLAG_ELEVATION_DP = 4

@Composable
fun CountryCard(
    modifier: Modifier,
    country: CountryEntity,
    currentCountry: Boolean,
    navigateToWeather: (String, Double, Double) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(COUNTRY_CARD_PADDING_DP.dp),
        elevation = COUNTRY_CARD_ELEVATION_DP.dp
    ) {
        Country(
            modifier = Modifier.then(
                if (currentCountry) {
                    Modifier.border(COUNTRY_BORDER_WIDTH_DP.dp, Color.Blue)
                } else {
                    Modifier
                }
            ),
            country,
            navigateToWeather
        )
    }
}

@Composable
fun Country(
    modifier: Modifier,
    country: CountryEntity,
    navigateToWeather: (String, Double, Double) -> Unit
) {
    Row(modifier = modifier.padding(COUNTRY_CONTENT_PADDING_DP.dp)) {
        Card(
            modifier = Modifier
                .width(COUNTRY_FLAG_WIDTH_DP.dp)
                .height(COUNTRY_FLAG_HEIGHT_DP.dp)
                .padding(COUNTRY_CONTENT_PADDING_DP.dp),
            elevation = COUNTRY_FLAG_ELEVATION_DP.dp
        ) {
            AsyncImage(
                model = country.flags.png,
                contentDescription = country.flags.alt,
                contentScale = ContentScale.FillBounds
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(COUNTRY_CONTENT_PADDING_DP.dp)
        ) {
            Text(text = country.name.common, style = MaterialTheme.typography.body1)
            Text(text = country.name.official, style = MaterialTheme.typography.body2)

            if (country.capital.isNotEmpty() && country.capitalInfo != null
                && country.capitalInfo?.latlng?.isNotEmpty() == true) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    country.capital.forEach { capital ->
                        TextButton(
                            colors = ButtonDefaults.textButtonColors(contentColor = Color.Blue),
                            onClick = {
                                navigateToWeather(
                                    capital,
                                    country.capitalInfo!!.latlng.first(),
                                    country.capitalInfo!!.latlng.last()
                                )
                            }
                        ) {
                            Text(text = "$capital Weather")
                        }
                    }
                }
            }
        }
    }
}