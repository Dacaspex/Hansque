package com.hansque.services.weather;

public class OpenWeatherMapService implements WeatherService {

    private String apiKey;

    public OpenWeatherMapService(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String testWeatherServiceMethod() {
        if (!apiKey.equals("key")) {
            throw new RuntimeException("Oh nose! Wrong api key");
        }
        return "sunny";
    }
}
