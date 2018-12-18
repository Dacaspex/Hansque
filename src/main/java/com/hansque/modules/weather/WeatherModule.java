package com.hansque.modules.weather;

import com.hansque.commands.Command;
import com.hansque.modules.Module;
import com.hansque.modules.weather.commands.GetWeatherCommand;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class WeatherModule implements Module {

    private GetWeatherCommand getWeatherCommand;

    public WeatherModule() {
        this.getWeatherCommand = new GetWeatherCommand();
    }

    public List<Command> getCommands() {
        return null;
    }

    public void execute(String command, String[] args, MessageReceivedEvent event) {
        if (command.equals("get")) {
            getWeatherCommand.execute(args, event);
        }
    }
}
