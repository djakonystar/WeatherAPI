package dev.djakonystar.weatherapi.data.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private lateinit var retrofit: Retrofit
    private lateinit var apiService: ApiService
    private lateinit var okHttpClient: OkHttpClient

    private fun getInstance(): Retrofit {
        if (!::retrofit.isInitialized) {
            retrofit = Retrofit.Builder()
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.weatherapi.com")
                .build()
        }

        return retrofit
    }

    private fun getOkHttpClient(): OkHttpClient {
        if (!::okHttpClient.isInitialized) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
        }

        return okHttpClient
    }

    fun getApiService(): ApiService {
        if (!::apiService.isInitialized) {
            apiService = getInstance().create(ApiService::class.java)
        }
        return apiService
    }
}
