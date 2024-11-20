package com.example.weather_api.controller;

import com.example.weather_api.model.Weather;
import com.example.weather_api.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/weather")
@RestController
public class WeatherController {
    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("{country}")
    public ResponseEntity<Weather> getWeather(@PathVariable String country) {
        return ResponseEntity.ok(weatherService.getWeather(country));
    }
}
