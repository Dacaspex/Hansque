package com.hansque.services.weather;

public interface WeatherService {

    public String getTemperatureByCity(String city);

    public Weather getCurrentWeatherByCity(String city);
}
