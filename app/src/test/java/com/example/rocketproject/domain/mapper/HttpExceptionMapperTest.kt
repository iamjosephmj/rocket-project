package com.example.rocketproject.domain.mapper

import android.app.Application
import com.example.rocketproject.R
import com.example.rocketproject.exception.RocketProjectException
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import java.io.IOException


internal class HttpExceptionMapperTest {

    private val application = mock<Application>()
    private val httpExceptionMapper = HttpExceptionMapper(application)

    @Test
    fun `WHEN the mapper is invoked with HttpException, THEN return RocketProjectException with correct message`() {
        val httpException = mock(HttpException::class.java)
        whenever(httpException.localizedMessage).thenReturn("HTTP error")

        val exception = httpExceptionMapper(httpException)

        assertThat(exception).isInstanceOf(RocketProjectException::class.java)
        assertThat(exception.localizedMessage).isEqualTo("HTTP error")
    }

    @Test
    fun `WHEN the mapper is invoked with IOException, THEN return RocketProjectException with no network message`() {
        whenever(application.getString(R.string.no_network)).thenReturn("No network")

        val exception = httpExceptionMapper(IOException())

        assertThat(exception).isInstanceOf(RocketProjectException::class.java)
        assertThat(exception.localizedMessage).isEqualTo("No network")
    }

    @Test
    fun `WHEN the mapper is invoke with other Exception, THEN return RocketProjectException with generic error message`() {
        whenever(application.getString(R.string.something_went_wrong)).thenReturn("Something went wrong")

        val exception = httpExceptionMapper(Exception())

        assertThat(exception).isInstanceOf(RocketProjectException::class.java)
        assertThat(exception.localizedMessage).isEqualTo("Something went wrong")
    }
}
