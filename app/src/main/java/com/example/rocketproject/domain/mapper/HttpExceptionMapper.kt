package com.example.rocketproject.domain.mapper

import android.app.Application
import com.example.rocketproject.R
import com.example.rocketproject.exception.RocketProjectException
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

internal class HttpExceptionMapper @Inject constructor(
    private val application: Application
) {
    operator fun invoke(ex: Throwable): Exception {

        return when (ex) {
            is HttpException -> {
                handleHttpException(ex)
            }

            is IOException -> {
                handleIOException()
            }

            else -> {
                handleNonHttpErrors()
            }
        }

    }

    private fun handleHttpException(ex: HttpException): Exception {
        val errorMessage =
            ex.localizedMessage ?: application.getString(R.string.something_went_wrong)

        return RocketProjectException(errorMessage)
    }

    private fun handleIOException(): Exception {
        val errorMessage = application.getString(R.string.no_network)

        return RocketProjectException(errorMessage)
    }

    private fun handleNonHttpErrors(): Exception {
        val errorMessage = application.getString(R.string.something_went_wrong)

        return RocketProjectException(errorMessage)
    }
}
