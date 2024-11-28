package com.example.weather_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${API_KEY}")
    private String key;

    private final RestTemplate restTemplate;

    @Autowired
    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Cacheable(value = "weather", key = "#location")
    public Object getWeather(String location) {
        String url = "https://api.weatherapi.com/v1/current.json?key=" + key +"&q=" + location;
        ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);

        return response.getBody();
    }

    @Cacheable(value = "forecast-weather", key = "#location")
    public Object getForecastWeather(String location, int day) {
        String url = "https://api.weatherapi.com/v1/forecast.json?key=" + key + "&q=" + location + "&days=" + day;
        ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);

        return response.getBody();
    }
}
