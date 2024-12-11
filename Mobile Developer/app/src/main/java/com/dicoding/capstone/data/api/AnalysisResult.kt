package com.dicoding.capstone.data.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnalysisResult(

    @field:SerializedName("image_url")
    val imageUri: String?,

    @field:SerializedName("prediction")
    val predictionText: String
) : Parcelable
