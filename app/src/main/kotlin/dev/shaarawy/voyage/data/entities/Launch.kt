package dev.shaarawy.voyage.data.entities


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Launch(
    @SerialName("id") val id: String = "", // 3b901da4-e396-437e-aa16-63593c4dec40
    @SerialName("provider") val provider: String = "" // Launch Library 2
)