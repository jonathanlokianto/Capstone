package com.dicoding.capstone.data.api

import com.dicoding.capstone.data.response.FileUploadResponse
import com.dicoding.capstone.data.response.Response
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @GET("articles")
    suspend fun getArticles(): Response

    @Multipart
    @POST("predict")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    ): FileUploadResponse
}


