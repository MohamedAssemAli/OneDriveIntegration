package com.assem.onedriveintegration.data.repository

import android.util.MalformedJsonException
import com.assem.onedriveintegration.data.models.ErrorResponse
import com.assem.onedriveintegration.utils.Resource
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.TimeoutException


suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {
    return try {
        val response = apiCall()
        when {
            response.isSuccessful -> {
                response.body()?.let { Resource.Success(it) }
                    ?: Resource.Error("HTTP 200: Empty response body")
            }

            else -> {
                try {
                    val statusResponse: ErrorResponse? =
                        Gson().fromJson(
                            response.errorBody()!!.charStream(),
                            object : TypeToken<ErrorResponse>() {}.type
                        )
                    Resource.Error(
                        statusResponse?.error?.message ?: "Something wrong happened!"
                    )

                } catch (exception: Throwable) {
                    Resource.Error(exception.message ?: "Can't parse error message!")
                }
            }
        }
    } catch (exception: Throwable) {
        handleApiError(exception)
    }
}

// Exception handling with detailed Resource.Error
private fun <T> handleApiError(exception: Throwable): Resource<T> {
    val message = when (exception) {
        is TimeoutException -> "Request timed out. Please try again."
        is IOException -> "Network error. Please check your connection."
        is HttpException -> {
            val statusCode = exception.code()
            when (statusCode) {
                400 -> "Bad Request"
                401 -> "Unauthorized. Please check your credentials."
                403 -> "Forbidden. Access is denied."
                404 -> "Resource not found."
                500 -> "Internal Server Error. Please try again later."
                503 -> "Service Unavailable. Please try again later."
                else -> "Unexpected HTTP Error: $statusCode"
            }
        }

        is JsonParseException, is MalformedJsonException -> "Malformed JSON received. Parsing failed."
        is IllegalArgumentException -> "Invalid argument provided. ${exception.message}"
        is IllegalStateException -> "Illegal application state. ${exception.message}"
        else -> "Unexpected error occurred: ${exception.message}"
    }
    return Resource.Error(message)
}
