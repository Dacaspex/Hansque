package com.hansque.modules.weather.commands;

import com.hansque.commands.Command;
import com.hansque.commands.CommandConfiguration;
import com.hansque.commands.argument.Argument;
import com.hansque.commands.argument.Arguments;
import com.hansque.services.weather.WeatherService;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class GetTemperatureCommand implements Command {

    private List<String> aliases;
    private WeatherService weatherService;

    public GetTemperatureCommand(List<String> aliases, WeatherService weatherService) {
        this.aliases = aliases;
        this.weatherService = weatherService;
    }

    @Override
    public CommandConfiguration configure() {
        return new CommandConfiguration.Builder()
                .setTrigger("temperature")
                .setDescription("Get the temperature for a city")
                .addArgument(new Argument("city", "City", Argument.Type.STRING))
                .addAliases(aliases)
                .build();
    }

    @Override
    public void execute(Arguments args, MessageReceivedEvent event) {
        String city = args.get("city").string();
        String temperature = weatherService.getTemperatureByCity(city);
        String message = String.format("The temperature in %s is %s", city, temperature);
        event.getChannel().sendMessage(message).queue();
    }
}
