package dev.shaarawy.voyage.data.entities


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    @SerialName("id") val id: Int = 0, // 46
    @SerialName("provider") val provider: String = "" // Launch Library 2
)