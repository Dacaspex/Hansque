package com.hansque.services.weather;

import com.hansque.modules.weather.Temperature;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class OpenWeatherMapService implements WeatherService {

    private String appId;

    public OpenWeatherMapService(String appId) {
        this.appId = appId;
    }

    @Override
    public String getTemperatureByCity(String city) {
        try {
            HttpResponse<JsonNode> json =
                    Unirest.get("http://api.openweathermap.org/data/2.5/weather")
                            .header("accept", "application/json")
                            .queryString("q", city)
                            .queryString("APPID", appId)
                            .asJson();

            double kelvin = json.getBody().getObject().getJSONObject("main").getDouble("temp");

            return String.valueOf(Temperature.kelvinToCelcius(kelvin));
        } catch (UnirestException e) {
            throw new RuntimeException("Could not fetch temperature");
        }
    }
}
