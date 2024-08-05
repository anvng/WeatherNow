package com.weathernow.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/v1/current.json")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") weatherApiKey: String
    ) : Response<WeatherModel>
}