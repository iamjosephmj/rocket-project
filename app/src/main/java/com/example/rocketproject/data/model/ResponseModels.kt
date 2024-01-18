package com.example.rocketproject.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Event(
    @SerialName("event_id") val eventId: Int,
    val provider: String
)
