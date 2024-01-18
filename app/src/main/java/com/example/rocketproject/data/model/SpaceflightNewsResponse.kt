package com.example.rocketproject.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SpaceflightNewsResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<SpaceflightNewsItem>
)
