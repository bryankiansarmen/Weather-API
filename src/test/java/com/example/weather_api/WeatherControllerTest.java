package com.example.weather_api;

import com.example.weather_api.controller.WeatherController;
import com.example.weather_api.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {
    @MockBean
    private WeatherService weatherService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetWeather() throws Exception {
        // mock response
        Object mockResponse = Map.of("location", Map.of("name", "London"), "current", Map.of("temp_c", 15));

        // stub the weather service call
        Mockito
                .when(weatherService.getWeather("London"))
                .thenReturn(mockResponse);

        // perform a get request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/weather/London"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.location.name").value("London"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.current.temp_c").value(15));
    }
}
