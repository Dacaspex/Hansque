package com.hansque.main;

import com.amihaiemil.eoyaml.YamlMapping;
import com.hansque.modules.weather.WeatherModule;
import com.hansque.modules.weather.commands.GetWeatherCommand;
import com.hansque.services.weather.OpenWeatherMapService;
import com.hansque.services.weather.WeatherService;

/**
 * Utility class to group all related object construction code
 */
public class Initialiser {

    /**
     * Loads the weather module. Constructs all objects required for the weather module to work.
     *
     * @param config Root level of the configuration
     * @return Weather module
     */
    public static WeatherModule getWeatherModule(YamlMapping config) {
        // Fetch details from config
        String apiKey = config.yamlMapping("services").yamlMapping("weather").string("open_weather_api_key");
        boolean enabled = Boolean.parseBoolean(config.yamlMapping("modules").yamlMapping("weather").string("enabled"));

        // Create services
        WeatherService weatherService = new OpenWeatherMapService(apiKey);

        // Create commands
        GetWeatherCommand getWeatherCommand = new GetWeatherCommand(weatherService);

        return new WeatherModule(enabled, getWeatherCommand);
    }

    // All other modules...
}
