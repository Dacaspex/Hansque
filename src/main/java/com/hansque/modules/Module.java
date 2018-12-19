package com.hansque.modules;

import com.hansque.commands.Command;
import com.hansque.commands.argument.Arguments;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public interface Module {

    public String getName();

    public boolean isEnabled();

    public void initialise();

    public List<Command> getCommands();

    public void execute(String command, Arguments args, MessageReceivedEvent event);

}
