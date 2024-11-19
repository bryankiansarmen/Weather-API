package com.example.weather_api.model;

public class Weather {
    private WeatherLocation location;
    private WeatherCurrent current;

    public Weather(WeatherLocation location, WeatherCurrent current) {
        this.location = location;
        this.current = current;
    }

    public WeatherLocation getLocation() {
        return location;
    }

    public void setLocation(WeatherLocation location) {
        this.location = location;
    }

    public WeatherCurrent getCurrent() {
        return current;
    }

    public void setCurrent(WeatherCurrent current) {
        this.current = current;
    }
}
