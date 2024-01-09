package entity.country

import kotlinx.serialization.Serializable

@Serializable
data class NameEntity(
    val common: String,
    val official: String
)
