package org.weatherapp.ui

import android.annotation.SuppressLint
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import entity.weather.LocationEntity
import org.weatherapp.R

private const val KEY_CITY_NAME = "cityName"
private const val KEY_LAT = "lat"
private const val KEY_LONG = "long"

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun App() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(id = R.string.app_name)) },
                    navigationIcon =
                    if (currentRoute?.startsWith(Route.WEATHER.name) == true) {
                        {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        }
                    } else {
                        null
                    }
                )
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = Route.COUNTRIES.name
            ) {
                composable(Route.COUNTRIES.name) {
                    CountriesScreen { cityName, lat, long ->
                        navController.navigate(
                            route = Route.WEATHER.name +
                                    "?$KEY_CITY_NAME=$cityName&$KEY_LAT=$lat&$KEY_LONG=$long"
                        )
                    }
                }
                composable(
                    route = Route.WEATHER.name +
                            "?$KEY_CITY_NAME={$KEY_CITY_NAME}" +
                            "&$KEY_LAT={$KEY_LAT}&$KEY_LONG={$KEY_LONG}",
                    arguments = listOf(
                        navArgument(KEY_CITY_NAME) {
                            type = NavType.StringType; defaultValue = "Unknown"
                        },
                        navArgument(KEY_LAT) {
                            type = NavType.FloatType; defaultValue = 0.0
                        },
                        navArgument(KEY_LONG) {
                            type = NavType.FloatType; defaultValue = 0.0
                        })
                ) {
                    WeatherScreen(
                        cityName = it.arguments?.getString(KEY_CITY_NAME).orEmpty(),
                        location = LocationEntity(
                            lat = it.arguments?.getFloat(KEY_LAT)?.toDouble() ?: 0.0,
                            long = it.arguments?.getFloat(KEY_LONG)?.toDouble() ?: 0.0
                        )

                    )
                }
            }
        }
    }
}