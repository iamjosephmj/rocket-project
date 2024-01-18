package com.example.rocketproject.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SpaceflightNewsItem(
    val id: Int,
    val title: String,
    val url: String,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("news_site") val newsSite: String,
    val summary: String,
    @SerialName("published_at") val publishedAt: String,
    @SerialName("updated_at") val updatedAt: String,
    val featured: Boolean,
    val launches: List<Launch>,
    val events: List<Event>
)
