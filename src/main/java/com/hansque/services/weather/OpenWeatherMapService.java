package com.hansque.services.weather;

import com.hansque.modules.weather.Temperature;
import com.hansque.modules.weather.Weather;
import com.hansque.modules.weather.Wind;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

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

    public Weather getCurrentWeatherByCity(String city) {
        try {
            HttpResponse<JsonNode> json =
                    Unirest.get("http://api.openweathermap.org/data/2.5/weather")
                            .header("accept", "application/json")
                            .queryString("q", city)
                            .queryString("APPID", appId)
                            .asJson();
            JSONObject data = json.getBody().getObject();

            // TODO: From the documentation of the api: Only really measured or calculated data is displayed in API response.
            // Therefore, we should be careful about these assumptions on the data. Preferably, we should try to load
            // them if they are present and if not, provide a default value ourselves. The current code simply throws
            // an error. 
            return new Weather(
                    data.getJSONArray("weather").getJSONObject(0).getString("description"),
                    new Temperature(data.getJSONObject("main").getDouble("temp")),
                    new Temperature(data.getJSONObject("main").getDouble("temp_min")),
                    new Temperature(data.getJSONObject("main").getDouble("temp_max")),
                    data.getJSONObject("main").getDouble("pressure"),
                    data.getJSONObject("main").getDouble("humidity"),
                    new Wind(
                            data.getJSONObject("wind").getDouble("speed"),
                            data.getJSONObject("wind").getDouble("deg")
                    ),
                    data.getJSONObject("clouds").getDouble("all")
            );
        } catch (UnirestException e) {
            throw new RuntimeException("Could not fetch weather: " + e.getMessage(), e);
        }
    }
}
