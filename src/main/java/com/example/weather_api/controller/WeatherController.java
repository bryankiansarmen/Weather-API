package com.example.weather_api.controller;

import com.example.weather_api.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1")
@RestController
public class WeatherController {
    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public Object getWeather(@RequestParam String location, @RequestParam(required = false) Integer day) {
        if (day == null) {
            return weatherService.getWeather(location);
        }

        return weatherService.getForecastWeather(location, day);
    }
}
