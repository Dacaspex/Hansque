package com.hansque.modules.weather.commands;

import com.hansque.commands.Command;
import com.hansque.commands.CommandConfiguration;
import com.hansque.commands.argument.Argument;
import com.hansque.commands.argument.Arguments;
import com.hansque.services.weather.WeatherService;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class GetWeatherCommand implements Command {

    private List<String> aliases;
    private CommandConfiguration configuration;
    private WeatherService weatherService;

    public GetWeatherCommand(List<String> aliases, WeatherService weatherService) {
        this.aliases = aliases;
        this.weatherService = weatherService;
    }

    @Override
    public void initialise() {
        // Create arguments
        Argument cityArgument = new Argument(
                "city",
                "The city of which you want to know the weather",
                Argument.Type.STRING,
                Argument.Constraint.REQUIRED
        );
        Argument heightArgument = new Argument(
                "height",
                "Optionally, the height at which you want to know the weather (for wind)",
                Argument.Type.INT,
                Argument.Constraint.OPTIONAL
        );

        // Build configuration
        configuration = new CommandConfiguration.Builder()
                .setTrigger("get")
                .addAliases(aliases)
                .setDescription("This command gives the weather")
                .addArgument(cityArgument)
                .addArgument(heightArgument)
                .build();
    }

    @Override
    public CommandConfiguration getConfiguration() {
        return configuration;
    }

    public void execute(Arguments args, MessageReceivedEvent event) {
        String city = args.get("city").string();
        int height = args.get("height").exists()
                ? args.get("height").integer()
                : 100;

        event.getChannel().sendMessage(
                "The weather in " + city + " at " + height + " meters is "
        ).queue();
    }
}
