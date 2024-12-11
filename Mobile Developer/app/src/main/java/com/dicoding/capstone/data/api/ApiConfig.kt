package com.dicoding.capstone.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private const val BASE_URL_1 = "https://wired-victor-441608-s8.et.r.appspot.com/"
    private const val BASE_URL_2 = "https://stress-detector-827762958393.asia-southeast2.run.app/"

    val instance1: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_1)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    val instance2: ApiService by lazy {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL_2)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
}

