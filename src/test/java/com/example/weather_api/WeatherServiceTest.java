package com.example.weather_api;

import com.example.weather_api.service.WeatherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@SpringBootTest
public class WeatherServiceTest {
    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private WeatherService weatherService;

    @Test
    void testGetWeather() {
        // mock response
        Object mockResponse = Map.of("location", Map.of("name", "London"), "current", Map.of("temp_c", 15));
        String mockUrl = "https://api.weatherapi.com/v1/current.json?key=mock-key&q=London";

        // stub the rest template call
        Mockito
                .when(restTemplate.getForEntity(mockUrl, Object.class))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        // set a test api key
        ReflectionTestUtils.setField(weatherService, "key", "mock-key");

        // call the service method
        Object result  = weatherService.getWeather("London");

        // assertions
        Assertions.assertNotNull(result);
        Assertions.assertTrue(((Map<?, ?>) result).containsKey("location"));
    }
}
