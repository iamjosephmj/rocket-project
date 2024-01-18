package com.example.rocketproject.domain.mapper

import android.app.Application
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock


internal class PublishedAtMapperTest {
    private val getZonedDateTime = GetZonedDateTime()
    private val application = mock<Application> {
        on { getString(any()) } doReturn "date not found"
    }

    private val mapper = PublishedAtMapper(
        getZonedDateTime = getZonedDateTime,
        application = application
    )

    @Test
    fun `WHEN UseCase is invoked AND the ZonedDateTime is valid, THEN return the formatted date time`() {
        val expected = "0:32 - 18/JANUARY/2024"

        val result = mapper("2024-01-18T00:32:36Z")

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `WHEN UseCase is invoked AND the ZonedDateTime mapping throws and exception, THEN return the error`() {
        val result = mapper("invalid")

        assertThat(result).isEqualTo("date not found")
    }


}