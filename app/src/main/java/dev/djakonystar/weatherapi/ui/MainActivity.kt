package dev.djakonystar.weatherapi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import dev.djakonystar.weatherapi.core.NetworkResult
import dev.djakonystar.weatherapi.data.retrofit.RetrofitClient
import dev.djakonystar.weatherapi.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val adapter by lazy { WeatherAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.recyclerView.adapter = adapter

        // request:
        viewModel.getForecast("Nukus", 7)

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            viewModel.getForecast("Nukus", 7)
        }

        viewModel.forecast.observe(this) {
            when (it) {
                is NetworkResult.Success -> {
                    setLoading(false)
                    adapter.models = it.data?.forecast?.forecastDay ?: listOf()
                }

                is NetworkResult.Error -> {
                    setLoading(false)
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading -> {
                    setLoading(true)
                }
            }
        }
    }

    private fun setLoading(loading: Boolean) {
        binding.apply {
            progressBar.isVisible = loading
            recyclerView.isEnabled = !loading
            swipeRefresh.isEnabled = !loading
        }
    }
}
