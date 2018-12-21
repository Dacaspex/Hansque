package com.hansque.main;

import com.amihaiemil.eoyaml.YamlMapping;
import com.amihaiemil.eoyaml.YamlNode;
import com.amihaiemil.eoyaml.YamlSequence;
import com.hansque.modules.management.ManagementModule;
import com.hansque.modules.management.commands.CleanMessagesCommand;
import com.hansque.modules.weather.WeatherModule;
import com.hansque.modules.weather.commands.GetTemperatureCommand;
import com.hansque.modules.weather.commands.GetWeatherCommand;
import com.hansque.services.weather.OpenWeatherMapService;
import com.hansque.services.weather.WeatherService;

import java.util.ArrayList;
import java.util.List;

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
        GetWeatherCommand getWeatherCommand = new GetWeatherCommand(
                Initialiser.loadAliasesForModule("weather", "get", config),
                weatherService
        );
        GetTemperatureCommand getTemperatureCommand = new GetTemperatureCommand(
                Initialiser.loadAliasesForModule("weather", "temperature", config),
                weatherService
        );

        return new WeatherModule(
                "weather",
                enabled,
                getWeatherCommand,
                getTemperatureCommand
        );
    }

    public static ManagementModule getManagementModule(YamlMapping config) {
        // Fetch details from config
        boolean enabled = Boolean.parseBoolean(config.yamlMapping("modules").yamlMapping("management").string("enabled"));

        // Create commands
        CleanMessagesCommand cleanMessagesCommand = new CleanMessagesCommand(
                Initialiser.loadAliasesForModule("management", "cleanmessages", config)
        );

        return new ManagementModule(
                "management",
                enabled,
                cleanMessagesCommand
        );
    }

    // All other modules...

    /**
     * Helper method to fetch all aliases of a command
     *
     * @param module  Module name
     * @param command Command name
     * @param config  Root level of the configuration
     * @return List of aliases for the command in the module
     */
    public static List<String> loadAliasesForModule(String module, String command, YamlMapping config) {
        // Get aliases node
        YamlSequence sequence = config
                .yamlMapping("modules")
                .yamlMapping(module)
                .yamlMapping("commands")
                .yamlMapping(command)
                .yamlSequence("aliases");

        // Extract aliases
        List<String> aliases = new ArrayList<>();
        for (YamlNode aliasNode : sequence.children()) {
            aliases.add(aliasNode.toString());
        }

        return aliases;
    }
}
