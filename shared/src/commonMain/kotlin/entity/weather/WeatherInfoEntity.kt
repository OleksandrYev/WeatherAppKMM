package entity.weather

import kotlinx.serialization.Serializable

@Serializable
data class WeatherInfoEntity(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
) {
    val iconUrl = "https://openweathermap.org/img/wn/$icon@2x.png"
}
