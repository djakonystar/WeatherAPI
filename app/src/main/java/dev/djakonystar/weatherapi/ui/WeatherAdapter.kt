package dev.djakonystar.weatherapi.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import dev.djakonystar.weatherapi.R
import dev.djakonystar.weatherapi.data.models.ForecastDay
import dev.djakonystar.weatherapi.databinding.ItemWeatherBinding

class WeatherAdapter : Adapter<WeatherAdapter.WeatherViewHolder>() {

    var models: List<ForecastDay> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class WeatherViewHolder(private val binding: ItemWeatherBinding) :
        ViewHolder(binding.root) {

        fun bind(forecastDay: ForecastDay) {
            binding.apply {
                ivCondition.load("https:${forecastDay.day.condition.icon}")

                tvDate.text = forecastDay.date
                tvMaxTemp.text = itemView.context.getString(
                    R.string.max_temp,
                    "%.2f".format(forecastDay.day.maxTempC)
                )
                tvMinTemp.text = itemView.context.getString(
                    R.string.min_temp,
                    "%.2f".format(forecastDay.day.minTempC)
                )
            }
        }
    }

    override fun getItemCount(): Int = models.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        val binding = ItemWeatherBinding.bind(itemView)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(models[position])
    }
}
