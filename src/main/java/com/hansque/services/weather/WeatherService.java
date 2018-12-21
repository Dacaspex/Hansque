package com.hansque.services.weather;

import com.hansque.modules.weather.Weather;

public interface WeatherService {

    public String getTemperatureByCity(String city);

    public Weather getCurrentWeatherByCity(String city);
}
