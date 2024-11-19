package com.example.weather_api.service;

import com.example.weather_api.model.Weather;
import com.example.weather_api.model.WeatherCondition;
import com.example.weather_api.model.WeatherCurrent;
import com.example.weather_api.model.WeatherLocation;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {
    public Weather getWeather() {
        WeatherLocation weatherLocation = new WeatherLocation("Manila", "Manila", "Philippines", 14.604, 120.982, "Asia/Manila", 1731985041L, "2024-11-19 10:57");
        WeatherCondition weatherCondition = new WeatherCondition("Partly cloudy", "//cdn.weatherapi.com/weather/64x64/day/116.png", 1003);
        WeatherCurrent weatherCurrent = new WeatherCurrent(1731984300L, "2024-11-19 10:45", 32.0, 89.6, 1, weatherCondition, 6.9, 11.2, 80, "E", 1013.0, 29.91, 0.0, 0.0, 63, 25, 38.5, 101.3, 29.5, 85.1, 33.0, 91.3, 22.2, 71.9, 10.0, 6.0, 6.3, 8.3, 13.4);

        return new Weather(weatherLocation, weatherCurrent);
    }
}
