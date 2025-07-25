package com.fake.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private lateinit var weatherRepository: WeatherRepository
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // UI Elements
    private lateinit var cityNameTextView: TextView
    private lateinit var temperatureTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var humidityTextView: TextView
    private lateinit var windSpeedTextView: TextView
    private lateinit var cityEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var locationButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize repository and location client
        weatherRepository = WeatherRepository()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Initialize UI elements
        initializeViews()

        // Set up click listeners
        setupClickListeners()

        // Check location permission on startup
        checkLocationPermission()

        // Load default weather (London as example)
        fetchWeatherData("London")
    }

    private fun initializeViews() {
        cityNameTextView = findViewById(R.id.cityNameTextView)
        temperatureTextView = findViewById(R.id.temperatureTextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        humidityTextView = findViewById(R.id.humidityTextView)
        windSpeedTextView = findViewById(R.id.windSpeedTextView)
        cityEditText = findViewById(R.id.cityEditText)
        searchButton = findViewById(R.id.searchButton)
        locationButton = findViewById(R.id.locationButton)
    }

    private fun setupClickListeners() {
        searchButton.setOnClickListener {
            val cityName = cityEditText.text.toString().trim()
            if (cityName.isNotEmpty()) {
                fetchWeatherData(cityName)
                cityEditText.text.clear()
            } else {
                Toast.makeText(this, "Please enter a city name", Toast.LENGTH_SHORT).show()
            }
        }

        locationButton.setOnClickListener {
            getCurrentLocationWeather()
        }
    }

    // Add this to your MainActivity if you want to use location features
    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    // Handle permission request result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted - you can now use location features
                    Toast.makeText(this, "Location permission granted", Toast.LENGTH_SHORT).show()
                } else {
                    // Permission denied - handle gracefully
                    Toast.makeText(this, "Location permission denied. You can still search by city name.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun fetchWeatherData(cityName: String) {
        showLoading()
        lifecycleScope.launch {
            weatherRepository.getCurrentWeather(cityName)
                .onSuccess { weatherResponse ->
                    updateUI(weatherResponse)
                }
                .onFailure { exception ->
                    hideLoading()
                    Toast.makeText(this@MainActivity, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun getCurrentLocationWeather() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {

            showLoading()
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        fetchWeatherByLocation(location.latitude, location.longitude)
                    } else {
                        hideLoading()
                        Toast.makeText(this, "Unable to get current location", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { exception ->
                    hideLoading()
                    Toast.makeText(this, "Error getting location: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Location permission is required", Toast.LENGTH_SHORT).show()
            checkLocationPermission()
        }
    }

    private fun fetchWeatherByLocation(latitude: Double, longitude: Double) {
        lifecycleScope.launch {
            weatherRepository.getCurrentWeatherByCoords(latitude, longitude)
                .onSuccess { weatherResponse ->
                    updateUI(weatherResponse)
                }
                .onFailure { exception ->
                    hideLoading()
                    Toast.makeText(this@MainActivity, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun updateUI(weather: WeatherResponse) {
        hideLoading()

        // Update UI elements with weather data
        cityNameTextView.text = weather.name
        temperatureTextView.text = "${weather.main.temp.toInt()}°C"
        descriptionTextView.text = weather.weather[0].description.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase() else it.toString()
        }
        humidityTextView.text = "Humidity: ${weather.main.humidity}%"
        windSpeedTextView.text = "Wind: ${weather.wind.speed} m/s"

        Toast.makeText(this, "Weather updated for ${weather.name}", Toast.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        cityNameTextView.text = "Loading..."
        temperatureTextView.text = "--°C"
        descriptionTextView.text = "Fetching weather data..."
        humidityTextView.text = "Humidity: --%"
        windSpeedTextView.text = "Wind: -- m/s"
    }

    private fun hideLoading() {
        // Loading state will be replaced by actual data in updateUI()
    }
}