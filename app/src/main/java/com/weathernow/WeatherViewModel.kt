package com.weathernow

import android.util.Log
import androidx.lifecycle.ViewModel

class WeatherViewModel : ViewModel() {
    fun getWeather(city: String) {
        Log.i("WeatherViewModel", "Getting weather for $city")
    }
}