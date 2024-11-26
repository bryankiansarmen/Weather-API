package com.example.weather_api;

import com.example.weather_api.service.WeatherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@SpringBootTest
public class WeatherServiceIntegrationTest {
    @Autowired
    private WeatherService weatherService;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        // create and bind MockRestServiceServer to the RestTemplate
        mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    }

    @Test
    void testGetWeather() {
        // mock the external API response
        mockServer
                .expect(MockRestRequestMatchers.requestTo("https://api.weatherapi.com/v1/current.json?key=test-key&q=London"))
                .andRespond(MockRestResponseCreators.withSuccess("{\"location\":{\"name\":\"London\"},\"current\":{\"temp_c\":15}}", MediaType.APPLICATION_JSON));

        // set test API key
        ReflectionTestUtils.setField(weatherService, "key", "test-key");

        // call the service method
        Object result = weatherService.getWeather("London");

        // assertions
        Assertions.assertNotNull(result);
        Assertions.assertTrue(((Map<?, ?>) result).containsKey("location"));
    }
}
