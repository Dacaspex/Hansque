package com.hansque.modules.weather.commands;

import com.hansque.commands.Command;
import com.hansque.commands.CommandConfiguration;
import com.hansque.commands.argument.Argument;
import com.hansque.commands.argument.Arguments;
import com.hansque.services.weather.WeatherService;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class GetWeatherCommand implements Command {

    private WeatherService weatherService;

    private Argument cityArgument;
    private Argument heightArgument;

    public GetWeatherCommand(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Override
    public CommandConfiguration configure() {
        // Configure arguments
        cityArgument = new Argument(
                "city",
                "The city of which you want to know the weather",
                Argument.Type.STRING,
                Argument.Constraint.REQUIRED
        );
        heightArgument = new Argument(
                "height",
                "Optionally, the height at which you want to know the weather (for wind)",
                Argument.Type.INT,
                Argument.Constraint.OPTIONAL
        );

        // Build command configuration
        return new CommandConfiguration.Builder()
                .setTrigger("get")
                .setDescription("This command gives the weather")
                .addArgument(cityArgument)
                .addArgument(heightArgument)
                .build();
    }

    public void execute(Arguments args, MessageReceivedEvent event) {
        String city = args.get("city").string();
        int height = args.get("height").exists()
                ? args.get("height").integer()
                : 100;

        event.getChannel().sendMessage(
                "The weather in " + city + " at " + height + " meters is " + weatherService.testWeatherServiceMethod()
        ).queue();
    }
}
