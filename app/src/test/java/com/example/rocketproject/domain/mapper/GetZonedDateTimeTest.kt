package com.example.rocketproject.domain.mapper

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException


internal class GetZonedDateTimeTest {
    private val getZonedDateTime = GetZonedDateTime()

    @Test
    fun `WHEN the CseVase is invoke THEN return ZonedDateTime for valid string`() {
        val testString = "2024-01-18T00:32:36Z"
        val expected = ZonedDateTime.parse(testString, DateTimeFormatter.ISO_ZONED_DATE_TIME)

        val result = getZonedDateTime(testString)

        assertThat(result).isEqualTo(expected)
    }

    @Test(expected = DateTimeParseException::class)
    fun `WHEN the UseCase is invoke AND the time format is not correct, THEN throws DateTimeParseException for invalid string`() {
        val invalidTestString = "invalid-date-time"
        getZonedDateTime(invalidTestString)
    }
}
