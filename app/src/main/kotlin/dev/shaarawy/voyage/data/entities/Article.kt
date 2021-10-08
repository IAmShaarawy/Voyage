package dev.shaarawy.voyage.data.entities


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    @SerialName("id") val id: Int = -1, // 3202
    @SerialName("title") val title: String = "", // Space station cargo mission grounded by launch pad fire
    @SerialName("url") val url: String = "", // https://spaceflightnow.com/2019/09/10/space-station-cargo-mission-grounded-by-launch-pad-fire/
    @SerialName("imageUrl") val imageUrl: String = "", // https://mk0spaceflightnoa02a.kinstacdn.com/wp-content/uploads/2019/09/EEHfulPUwAU3D00-326x245.jpeg
    @SerialName("newsSite") val newsSite: String = "", // Spaceflight Now
    @SerialName("summary") val summary: String = "",
    @SerialName("publishedAt") val publishedAt: String = "", // 2019-09-10T23:29:33.000Z
    @SerialName("updatedAt") val updatedAt: String = "", // 2021-05-18T13:45:18.014Z
    @SerialName("featured") val featured: Boolean = false, // true
    @SerialName("launches") val launches: List<Launch> = listOf(),
    @SerialName("events") val events: List<Event> = listOf()
)