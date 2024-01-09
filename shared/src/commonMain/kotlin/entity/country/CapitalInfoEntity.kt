package entity.country

import kotlinx.serialization.Serializable

@Serializable
data class CapitalInfoEntity(
    val latlng: List<Double> = emptyList()
)
