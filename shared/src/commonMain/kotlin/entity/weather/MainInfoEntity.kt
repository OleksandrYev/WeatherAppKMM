package entity.weather

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class MainInfoEntity(
    @JsonNames("feels_like")
    val feelsLike: Double,

    @JsonNames("humidity")
    val humidity: Int,

    @JsonNames("pressure")
    val pressure: Int,

    @JsonNames("temp")
    val temperature: Double,

    @JsonNames("temp_max")
    val temperatureMax: Double,

    @JsonNames("temp_min")
    val temperatureMin: Double
)
