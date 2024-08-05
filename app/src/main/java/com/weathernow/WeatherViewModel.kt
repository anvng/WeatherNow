package com.weathernow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weathernow.api.Constant
import com.weathernow.api.NetworkResponse
import com.weathernow.api.RetrofitInstance
import com.weathernow.api.WeatherModel
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherData = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherData: LiveData<NetworkResponse<WeatherModel>> = _weatherData


    fun getWeather(city: String) {
        _weatherData.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = weatherApi.getWeather(city, Constant.weatherApiKey)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _weatherData.value = NetworkResponse.Success(it)
                    }
                } else {
                    _weatherData.value = NetworkResponse.Error("Failed to fetch weather data!")

                }
            }catch (e: Exception){
                _weatherData.value = NetworkResponse.Error("Failed to fetch weather data!")
            }
        }
    }
}