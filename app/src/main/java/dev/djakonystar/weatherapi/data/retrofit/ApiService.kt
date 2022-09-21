package dev.djakonystar.weatherapi.data.retrofit

import dev.djakonystar.weatherapi.data.models.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/v1/forecast.json")
    suspend fun getWeeklyForecast(
        @Query("key") apiKey: String,
        @Query("q") city: String,
        @Query("days") days: Int,
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no"
    ): Response<ForecastResponse>
}
