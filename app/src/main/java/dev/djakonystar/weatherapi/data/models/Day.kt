package dev.djakonystar.weatherapi.data.models

import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("maxtemp_c")
    val maxTempC: Double,
    @SerializedName("mintemp_c")
    val minTempC: Double,
    val condition: Condition
)
