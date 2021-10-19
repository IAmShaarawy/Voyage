package dev.shaarawy.voyage.data.entities

import kotlinx.serialization.Serializable

/**
 * @author Mohamed Elshaarawy on Oct 19, 2021.
 */
@Serializable
enum class SortingSpecifier(val value: String) {
    TITLE("title"),
    PUBLISH_DATE("publishedAt"),
}