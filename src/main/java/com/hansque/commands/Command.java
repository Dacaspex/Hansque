package com.hansque.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface Command {

    public String getTrigger();

    public String getDescription();

    public void execute(String[] args, MessageReceivedEvent event);
}
