package entity.weather

import kotlinx.serialization.Serializable

@Serializable
data class WeatherEntity(
    val base: String,
    val clouds: CloudsEntity,
    val cod: Int,
    val coord: LocationEntity,
    val dt: Int,
    val id: Int,
    val main: MainInfoEntity,
    val name: String,
    val timezone: Int,
    val visibility: Int,
    val weather: List<WeatherInfoEntity>,
)
