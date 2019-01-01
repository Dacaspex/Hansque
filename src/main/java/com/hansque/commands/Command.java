package com.hansque.commands;

import com.hansque.commands.argument.Arguments;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface Command {

    /**
     * Initialises required objects and/or services specific to this command.
     * It is only ran once on startup before any other method.
     */
    public void initialise();

    /**
     * @return The configuration for this command
     */
    public CommandConfiguration getConfiguration();

    /**
     * Method that is executed when the command is triggered.
     *
     * @param args  Arguments passed from message
     * @param event Event that fired the command
     */
    public void execute(Arguments args, MessageReceivedEvent event);
}
