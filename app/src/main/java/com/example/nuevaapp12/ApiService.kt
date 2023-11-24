package com.example.nuevaapp12

import retrofit2.Call
import retrofit2.http.GET
import kotlin.Any

interface ApiService {
    @GET("/data")
    fun getLatestData(): Call<List<YourDataModel>> // Usando Any como tipo genérico
}
