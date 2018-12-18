package com.hansque.modules.weather.commands;

import com.hansque.commands.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class GetWeatherCommand implements Command {

    public String getTrigger() {
        return "get";
    }

    public String getDescription() {
        return "Gets the weather for a given city.";
    }

    public void execute(String[] args, MessageReceivedEvent event) {
        if (args.length == 1) {
            event.getChannel().sendMessage(
                    "Dear "
                            + event.getMessage().getAuthor().getName()
                            + " the weather in "
                            + args[0]
                            + " is sunny"
            ).queue();
        } else {
            event.getChannel().sendMessage("Please provide a city").queue();
        }
    }
}
