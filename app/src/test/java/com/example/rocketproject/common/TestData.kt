package com.example.rocketproject.common

import com.example.rocketproject.data.model.SpaceflightNewsItem
import com.example.rocketproject.data.model.SpaceflightNewsResponse


internal val spaceFlightItem = SpaceflightNewsItem(
    id = 1,
    title = "title",
    url = "url",
    imageUrl = "imageUrl",
    newsSite = "newsSite",
    summary = "summary",
    publishedAt = "publishedAt",
    updatedAt = "updatedAt",
    featured = false,
    launches = emptyList(),
    events = emptyList()
)

internal val validSpaceFlightResponse = SpaceflightNewsResponse(
    count = 1,
    next = null,
    previous = null,
    results = listOf(
        spaceFlightItem
    )
)
