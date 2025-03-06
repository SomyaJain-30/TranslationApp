package com.example.translationapp.rest

import androidx.compose.foundation.gestures.rememberTransformableState
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiModule {

    fun getNetworkInstance(): Retrofit {
        return Retrofit.Builder().baseUrl("").addConverterFactory(GsonConverterFactory.create()).build()
}

    fun httpClient(): OkHttpClient{
        return OkHttpClient.Builder().build()
    }

    fun getApiInstance(retrofit: Retrofit): ApiInterface{
        return retrofit.create(ApiInterface::class.java)
    }
}