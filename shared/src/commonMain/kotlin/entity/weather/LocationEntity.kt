package entity.weather

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class LocationEntity(
    @JsonNames("lat")
    val lat: Double,

    @JsonNames("lng", "lon", "long")
    val long: Double
)
