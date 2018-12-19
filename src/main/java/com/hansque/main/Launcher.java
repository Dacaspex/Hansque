package com.hansque.main;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlMapping;
import com.hansque.core.Dispatcher;
import com.hansque.modules.weather.WeatherModule;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import java.io.File;

public class Launcher {

    public void run() throws Exception {
        YamlMapping yaml = Yaml.createYamlInput(new File("config.yaml")).readYamlMapping();
        YamlMapping botMapping = yaml.yamlMapping("bot");
        String apiKey = botMapping.string("api_key");

        JDA jda = new JDABuilder(apiKey).build();

        // Maybe move the dispatcher initialization and module registering to a different class
        String commandPrefix = botMapping.string("command_prefix");
        Dispatcher dispatcher = new Dispatcher(jda, commandPrefix);

        YamlMapping moduleMapping = botMapping.yamlMapping("modules");

        if (moduleMapping.yamlMapping("weather").string("enabled").equals("true")) {
            dispatcher.registerModule("weather", new WeatherModule());
        }

        dispatcher.loadAliases(moduleMapping);
    }

    public static void main(String[] args) throws Exception {
        (new Launcher()).run();
    }
}
