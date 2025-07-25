# 🌦️ WeatherApp

An Android application that provides real-time weather information using [WeatherAPI.com](https://www.weatherapi.com/) via RapidAPI.

---

## 📌 API Choice: WeatherAPI.com

### ✅ Why I Chose WeatherAPI.com

I selected **WeatherAPI.com** from RapidAPI because of its rich features, reliability, and ease of integration with mobile platforms. Here's why it stood out:

---

### 1. **Comprehensive Weather Data**
- Real-time current weather conditions  
- 14-day weather forecasts  
- Historical weather records  
- Air quality information  
- Astronomy data (sunrise, sunset, moon phases)  
- Weather alerts and warnings  

---

### 2. **High Reliability**
- 99.99% uptime guarantee  
- Fast global response times (<100ms)  
- Trusted by thousands of developers  

---

### 3. **Mobile-Optimized Features**
- Supports location-based queries (city, GPS coordinates, IP, postal code)  
- Multiple measurement units (Celsius/Fahrenheit, km/h or mph)  
- Weather icon URLs for intuitive UX  
- Rich metadata: humidity, "feels like" temperature, UV index, visibility  

---

### 4. **Excellent Developer Experience**
- RESTful API with extensive documentation  
- Clean JSON responses (easy to parse with Gson)  
- Free tier via RapidAPI for development and testing  
- Scalable pricing for future production use  

---

### 5. **Ideal for a Mobile Weather App**

Weather is a **daily-use** feature for mobile users. This API enables:
- **Daily planning** before leaving home  
- **Travel prep** by checking destination conditions  
- **Health awareness** via air quality & UV index  
- **Safety alerts** for severe weather or sudden changes  

---

## 📱 Mobile App Relevance

Weather data is among the most frequently accessed mobile features. This app is highly relevant because:

- **Location Awareness**: Uses GPS for hyper-local conditions  
- **Push Notifications**: Can notify users of critical changes  
- **Offline Mode**: Caches recent weather for poor connectivity  
- **Customization**: Users can save preferred units and locations  
- **Seamless Integration**: Fits with other apps like calendars, travel planners, etc.

---

## 🔧 Technologies Used

- **Android SDK** – Native app development  
- **Kotlin** – Main programming language  
- **Retrofit 2** – HTTP client for API calls  
- **OkHttp** – Networking layer with logging interceptors  
- **Gson** – For JSON parsing  
- **Coroutines** – For asynchronous background tasks  
- **RapidAPI** – For API key and request handling  

---

## 🚀 Features

- Live weather updates  
- 7-day forecast  
- Temperatures in °C and °F  
- Wind speed and direction  
- Humidity and pressure  
- Weather condition descriptions with icons  
- Auto location detection  

---

## ⚙️ Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/Rhea0524/WeatherApp.git
2. Open the project in Android Studio
3.Get your API key from RapidAPI's WeatherAPI.com
4.Replace YOUR_RAPIDAPI_KEY_HERE in WeatherRepository.kt
5.Build and run on emulator or Android device

## 🔌 API Endpoints Used

- `GET /current.json` — Get current weather  
- `GET /forecast.json` — Get multi-day forecast  

---

## 📦 Dependencies

```gradle
implementation 'com.squareup.retrofit2:retrofit:2.9.0'  
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'  
implementation 'com.squareup.okhttp3:okhttp:4.11.0'  
implementation 'com.squareup.okhttp3:logging-interceptor:4.11.0'  
implementation 'com.google.code.gson:gson:2.10.1'  
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4'  

## 🧱 Architecture

The app follows clean architecture principles:

- **Data Layer**: API service interfaces and repositories  
- **Domain Layer**: Models and business logic  
- **Presentation Layer**: Activities and UI components  

---

## 🌟 Future Enhancements

- Home screen weather widgets  
- Weather-based outfit suggestions  
- Calendar integration for weather-aware event planning  
- Push alerts for severe weather  
- Multi-location tracking  
- Weather trend charts and graphs  

