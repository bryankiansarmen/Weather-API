package com.example.weather_api.service;

import com.example.weather_api.model.Weather;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@PropertySource("classpath:env.properties")
public class WeatherService {
    @Value("${API_KEY}")
    private String key;


    @Cacheable(value = "weather", key = "#country")
    public Weather getWeather(String country) {
        RestClient restClient = RestClient.create();

        return restClient.get()
                .uri("https://api.weatherapi.com/v1/current.json?key=" + key +"&q=" + country)
                .retrieve()
                .body(Weather.class);
    }
}
