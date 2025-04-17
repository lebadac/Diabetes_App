package com.dac.diabetes

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/predict")
    fun predictDiabetes(@Body request: DiabetesRequest): Call<DiabetesResponse>
}