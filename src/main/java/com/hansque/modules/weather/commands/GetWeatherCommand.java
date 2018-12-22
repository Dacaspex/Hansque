package com.hansque.modules.weather.commands;

import com.hansque.commands.Command;
import com.hansque.commands.CommandConfiguration;
import com.hansque.commands.argument.Argument;
import com.hansque.commands.argument.Arguments;
import com.hansque.services.weather.Weather;
import com.hansque.services.weather.WeatherService;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.Color;
import java.util.List;

public class GetWeatherCommand implements Command {

    private List<String> aliases;
    private WeatherService weatherService;

    private Argument cityArgument;
    private Argument heightArgument;

    public GetWeatherCommand(List<String> aliases, WeatherService weatherService) {
        this.aliases = aliases;
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
                .addAliases(aliases)
                .setDescription("This command gives the weather")
                .addArgument(cityArgument)
                .addArgument(heightArgument)
                .build();
    }

    public void execute(Arguments args, MessageReceivedEvent event) {
        String city = args.get("city").string();
        Weather weather = weatherService.getCurrentWeatherByCity(city);

        // Build an embedded message with all the weather details
        MessageEmbed message = new EmbedBuilder()
                .setColor(new Color(118, 207, 242))
                .setAuthor("Weather service")
                .setDescription("Weather for " + city)
                .addField(
                        "Temperature",
                        weather.getTemperature().celsius() + "°C",
                        true
                )
                .addField(
                        "wind",
                        weather.getWind().getSpeed() + "m/s",
                        true
                )
                .build();

        event.getChannel().sendMessage(message).queue();
    }
}
