package entity.country

import kotlinx.serialization.Serializable

@Serializable
data class CountryEntity(
    val capital: List<String> = emptyList(),
    val capitalInfo: CapitalInfoEntity? = null,
    val flags: FlagEntity,
    val name: NameEntity,
    val cca2: String
)
