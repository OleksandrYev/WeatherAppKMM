package entity.country

import kotlinx.serialization.Serializable

@Serializable
data class FlagEntity(
    val alt: String? = "",
    val png: String,
    val svg: String
)
