package dev.djakonystar.weatherapi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.djakonystar.weatherapi.core.Constants.API_KEY
import dev.djakonystar.weatherapi.core.NetworkResult
import dev.djakonystar.weatherapi.data.models.ForecastResponse
import dev.djakonystar.weatherapi.data.retrofit.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val apiService = RetrofitClient.getApiService()

    private var _forecast: MutableLiveData<NetworkResult<ForecastResponse>> = MutableLiveData()
    val forecast: LiveData<NetworkResult<ForecastResponse>> = _forecast

    fun getForecast(city: String, days: Int) {
        _forecast.value = NetworkResult.Loading()
        viewModelScope.launch {
            try {
                val response = apiService.getWeeklyForecast(API_KEY, city, days)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _forecast.value = NetworkResult.Success(it)
                        }
                    } else {
                        _forecast.value = NetworkResult.Error(response.message())
                    }
                }
            } catch (e: Exception) {
                _forecast.value = NetworkResult.Error(e.message)
            }
        }
    }
}
