package com.hansque.main;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlMapping;
import com.hansque.core.Hansque;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import java.io.File;

public class Launcher {

    /**
     * Entry point of the program
     *
     * @throws Exception
     */
    public void run() throws Exception {
        YamlMapping yaml = Yaml.createYamlInput(new File("config.yaml")).readYamlMapping();
        YamlMapping botMapping = yaml.yamlMapping("bot");

        String apiKey = botMapping.string("api_key");

        JDA jda = new JDABuilder(apiKey).build();

        // Maybe move the hansque initialization and module registering to a different class
        String commandPrefix = botMapping.string("command_prefix");
        Hansque hansque = new Hansque(jda, commandPrefix);

        // Register modules
        hansque.registerModules(
                Initialiser.getWeatherModule(yaml)
        );

        try {
            hansque.initialise();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void main(String[] args) throws Exception {
        (new Launcher()).run();
    }
}
