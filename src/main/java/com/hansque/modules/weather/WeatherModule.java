package com.hansque.modules.weather;

import com.hansque.commands.Command;
import com.hansque.commands.argument.Arguments;
import com.hansque.modules.Module;
import com.hansque.modules.weather.commands.GetTemperatureCommand;
import com.hansque.modules.weather.commands.GetWeatherCommand;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeatherModule implements Module {

    private String name;
    private boolean enabled;

    private GetWeatherCommand getWeatherCommand;
    private GetTemperatureCommand getTemperatureCommand;

    public WeatherModule(
            String name,
            boolean enabled,
            GetWeatherCommand getWeatherCommand,
            GetTemperatureCommand getTemperatureCommand
    ) {
        this.name = name;
        this.enabled = enabled;
        this.getWeatherCommand = getWeatherCommand;
        this.getTemperatureCommand = getTemperatureCommand;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void initialise() {
        // Method body intentionally left black
        // Services that need initialisation can be initialised here
    }

    // TODO: nicer implementation of getCommands()
    public List<Command> getCommands() {
        return new ArrayList<>(
                Arrays.asList(
                        getWeatherCommand,
                        getTemperatureCommand
                )
        );
    }

    public void execute(Command command, Arguments args, MessageReceivedEvent event) {
        command.execute(args, event);
    }
}
