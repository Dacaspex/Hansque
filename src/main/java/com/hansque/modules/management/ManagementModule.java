package com.hansque.modules.management;

import com.hansque.commands.Command;
import com.hansque.commands.argument.Arguments;
import com.hansque.modules.Module;
import com.hansque.modules.management.commands.CleanMessagesCommand;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManagementModule implements Module {

    private String name;
    private boolean enabled;

    private CleanMessagesCommand cleanMessagesCommand;

    public ManagementModule(
            String name,
            boolean enabled,
            CleanMessagesCommand cleanMessagesCommand
    ) {
        this.name = name;
        this.enabled = enabled;
        this.cleanMessagesCommand = cleanMessagesCommand;
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

    }

    public List<Command> getCommands() {
        return new ArrayList<>(
                Arrays.asList(
                        cleanMessagesCommand
                )
        );
    }

    public void execute(Command command, Arguments args, MessageReceivedEvent event) {
        command.execute(args, event);
    }

}
