package com.hansque.commands;

import com.hansque.commands.argument.Arguments;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface Command {

    public CommandConfiguration configure();

    public void execute(Arguments args, MessageReceivedEvent event);
}
