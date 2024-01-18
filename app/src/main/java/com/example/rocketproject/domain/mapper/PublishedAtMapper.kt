package com.example.rocketproject.domain.mapper

import android.app.Application
import com.example.rocketproject.R
import java.time.format.DateTimeParseException
import javax.inject.Inject

internal class PublishedAtMapper @Inject constructor(
    private val getZonedDateTime: GetZonedDateTime,
    private val application: Application
) {
    operator fun invoke(publishedAt: String): String {
        return try {
            val zonedDateTime = getZonedDateTime(publishedAt = publishedAt)
            "${zonedDateTime.hour}:${zonedDateTime.minute} - ${zonedDateTime.dayOfMonth}/${zonedDateTime.month}/${zonedDateTime.year}"
        } catch (ex: DateTimeParseException) {
            application.getString(R.string.date_time_value_invalid)
        }
    }
}
