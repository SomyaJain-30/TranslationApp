package com.example.translationapp.rest


sealed class NetworkCallResponse<T>(
    val data: T? = null,
    val message: String? = null,
    val isLoading: Boolean = false
) {

    class Loading<T>(isLoading: Boolean = false) : NetworkCallResponse<T>(isLoading = isLoading)
    class Success<T>(data: T? = null) : NetworkCallResponse<T>(data = data)
    class Error<T>(message: String? = null) : NetworkCallResponse<T>(message = message)

}