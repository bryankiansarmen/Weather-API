package com.example.weather_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@PropertySource("classpath:env.properties")
public class WeatherService {
    @Value("${API_KEY}")
    private String key;

    private final RestTemplate restTemplate;

    @Autowired
    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Cacheable(value = "weather", key = "#country")
    public Object getWeather(String country) {
        String url = "https://api.weatherapi.com/v1/current.json?key=" + key +"&q=" + country;
        ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);

        return response.getBody();
    }
}
