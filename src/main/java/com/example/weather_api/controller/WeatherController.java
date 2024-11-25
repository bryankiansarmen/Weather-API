package com.example.weather_api.controller;

import com.example.weather_api.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Object getWeather(@PathVariable String country) {
        return weatherService.getWeather(country);
    }
}
