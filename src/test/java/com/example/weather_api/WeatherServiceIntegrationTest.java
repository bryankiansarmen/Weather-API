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

import java.util.List;
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
    void testGetWeatherWithoutDays() {
        // mock the external API response
        mockServer
                .expect(MockRestRequestMatchers.requestTo("https://api.weatherapi.com/v1/current.json?key=test-key&q=London"))
                .andRespond(MockRestResponseCreators.withSuccess("""
                        {
                            "location": {
                                "name": "London"
                            },
                            "current": {
                                "temp_c": 2.4
                            }
                        }""", MediaType.APPLICATION_JSON));

        // set test API key
        ReflectionTestUtils.setField(weatherService, "key", "test-key");

        // call the service method
        Object result = weatherService.getWeather("London");

        // assertions
        Assertions.assertNotNull(result, "Result should not be null");

        // cast result to Map for further verification
        Map<?, ?> resultMap = (Map<?, ?>) result;

        // check top-level keys
        Assertions.assertTrue(resultMap.containsKey("location"), "'location' key should be present");
        Assertions.assertTrue(resultMap.containsKey("current"), "'current' key should be present");

        // check nested location values
        Map<?, ?> location = (Map<?, ?>) resultMap.get("location");
        Assertions.assertEquals("London", location.get("name"), "'name' under 'location' should be 'London'");

        // check nested current values
        Map<?, ?> current = (Map<?, ?>) resultMap.get("current");
        Assertions.assertEquals(15, current.get("temp_c"), "'temp_c' under 'current' should be 15");
    }

    @Test
    void testGetWeatherWithDays() {
        // mock the external API response
        mockServer
                .expect(MockRestRequestMatchers.requestTo("https://api.weatherapi.com/v1/forecast.json?key=test-key&q=London&days=3"))
                .andRespond(MockRestResponseCreators.withSuccess("""
                        {
                            "location": {
                                "name": "London"
                            },
                            "current": {
                                "temp_c": 15
                            },
                            "forecast": {
                                "forecastday": [
                                    {
                                        "day": {
                                            "uv": 0.7
                                        }
                                    },
                                    {
                                        "day": {
                                            "uv": 0.5
                                        }
                                    },
                                    {
                                        "day": {
                                            "uv": 1.3
                                        }
                                    }
                                ]
                            }
                        }""", MediaType.APPLICATION_JSON));

        // set test API key
        ReflectionTestUtils.setField(weatherService, "key", "test-key");

        // call the service method
        Object result = weatherService.getForecastWeather("London", 3);

        // assertions
        Assertions.assertNotNull(result, "Result should not be null");

        // cast result to Map for further verification
        Map<?, ?> resultMap = (Map<?, ?>) result;

        // check top-level keys
        Assertions.assertTrue(resultMap.containsKey("location"), "'location' key should be present");
        Assertions.assertTrue(resultMap.containsKey("current"), "'current' key should be present");
        Assertions.assertTrue(resultMap.containsKey("forecast"), "'forecast' key should be present");

        // check nested location values
        Map<?, ?> location = (Map<?, ?>) resultMap.get("location");
        Assertions.assertEquals("London", location.get("name"), "'name' under 'location' should be 'London'");

        // check nested current values
        Map<?, ?> current = (Map<?, ?>) resultMap.get("current");
        Assertions.assertEquals(15, current.get("temp_c"), "'temp_c' under 'current' should be 15");

        // check nested forecast values
        Map<?, ?> forecast = (Map<?, ?>) resultMap.get("forecast");
        Assertions.assertTrue(forecast.containsKey("forecastday"), "'forecastday' key should be present in 'forecast'");

        // check forecastday list
        List<Map<?, ?>> forecastDayList = (List<Map<?, ?>>) forecast.get("forecastday");
        Assertions.assertEquals(3, forecastDayList.size(), "'forecastday' should contain 3 entries");

        // check individual forecast days
        Assertions.assertEquals(0.7, ((Map<?, ?>) forecastDayList.get(0).get("day")).get("uv"), "UV value for day 1 should be 0.7");
        Assertions.assertEquals(0.5, ((Map<?, ?>) forecastDayList.get(1).get("day")).get("uv"), "UV value for day 2 should be 0.5");
        Assertions.assertEquals(1.3, ((Map<?, ?>) forecastDayList.get(2).get("day")).get("uv"), "UV value for day 3 should be 1.3");
    }
}
