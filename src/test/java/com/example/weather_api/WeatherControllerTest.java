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

import java.util.List;
import java.util.Map;

@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {
    @MockBean
    private WeatherService weatherService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetWeatherWithoutDay() throws Exception {
        // mock response
        Object mockResponse = Map.of(
                "location", Map.of("name", "London"),
                "current", Map.of("temp_c", 15)
        );

        // stub the weather service call
        Mockito
                .when(weatherService.getWeather("London"))
                .thenReturn(mockResponse);

        // perform a get request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/weather?location=London"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.location.name").value("London"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.current.temp_c").value(15));
    }

    @Test
    void testGetWeatherWithDay() throws Exception {
        // mock response
        Object mockResponse = Map.of(
                "location", Map.of("name", "London"),
                "current", Map.of("temp_c", 15),
                "forecast", Map.of(
                        "forecastday", List.of(
                                Map.of("day", Map.of("uv", 0.7)),
                                Map.of("day", Map.of("uv", 0.5)),
                                Map.of("day", Map.of("uv", 1.3))
                        )
                )
        );

        // stub the weather service call
        Mockito
                .when(weatherService.getForecastWeather("London", 3))
                .thenReturn(mockResponse);

        // perform a get request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/weather?location=London&days=3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.location.name").value("London"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.current.temp_c").value(15))
                .andExpect(MockMvcResultMatchers.jsonPath("$.forecast.forecastday[0].day.uv").value(0.7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.forecast.forecastday[1].day.uv").value(0.5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.forecast.forecastday[2].day.uv").value(1.3));
    }

}
