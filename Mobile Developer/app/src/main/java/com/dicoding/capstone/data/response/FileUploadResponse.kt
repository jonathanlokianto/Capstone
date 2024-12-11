package com.dicoding.capstone.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FileUploadResponse(

    @SerialName("prediction")
    val prediction: Float? = null, // Ganti Any? dengan Float? atau Double?

    @SerialName("status")
    val status: String? = null
)

