package com.hansque.modules;

import com.hansque.commands.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public interface Module {

    public List<Command> getCommands();

    public void execute(String command, String[] args, MessageReceivedEvent event);

}
