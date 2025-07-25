package com.fake.weatherapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository {
    private val apiService = ApiClient.weatherApiService
    private val apiKey = "1709f786eamsh4040082adbdbec3p1c72cejsnff24cb1a52eb" // Replace with your actual API key

    suspend fun getCurrentWeather(cityName: String): Result<WeatherResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getCurrentWeather(cityName, apiKey)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Weather data not found"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun getCurrentWeatherByCoords(lat: Double, lon: Double): Result<WeatherResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getCurrentWeatherByCoords(lat, lon, apiKey)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Weather data not found"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}