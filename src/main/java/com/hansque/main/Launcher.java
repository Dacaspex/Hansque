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
        String apiKey = yaml.yamlMapping("bot").string("api_key");

        JDA jda = new JDABuilder(apiKey).build();
        Dispatcher dispatcher = new Dispatcher(jda);

        dispatcher.registerModule("weather", new WeatherModule());
    }

    public static void main(String[] args) throws Exception {
        (new Launcher()).run();
    }
}
