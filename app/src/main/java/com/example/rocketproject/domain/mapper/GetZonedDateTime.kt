package com.example.rocketproject.domain.mapper

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

internal class GetZonedDateTime @Inject constructor() {
    operator fun invoke(publishedAt: String): ZonedDateTime {
        val formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
        return ZonedDateTime.parse(publishedAt, formatter)
    }
}
