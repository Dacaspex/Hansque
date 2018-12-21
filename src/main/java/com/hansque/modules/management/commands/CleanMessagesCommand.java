package com.hansque.modules.management.commands;

import com.hansque.commands.Command;
import com.hansque.commands.CommandConfiguration;
import com.hansque.commands.argument.Argument;
import com.hansque.commands.argument.Arguments;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CleanMessagesCommand implements Command {

    private List<String> aliases;

    private Argument numberOfMessages;

    public CleanMessagesCommand(List<String> aliases) {
        this.aliases = aliases;
    }

    @Override
    public CommandConfiguration configure() {
        numberOfMessages = new Argument(
                "number",
                "The number of messages that need to be removed",
                Argument.Type.INT,
                Argument.Constraint.OPTIONAL
        );

        return new CommandConfiguration.Builder()
                .setTrigger("cleanmessages")
                .addAliases(aliases)
                .setDescription("This command cleans up messages in a channel")
                .addArgument(numberOfMessages)
                .build();
    }

    @Override
    public void execute(Arguments args, MessageReceivedEvent event) {
        // TODO: discord API does not allow over 100 messages to be
        // TODO: retrieved at once, so make a new thread to .complete()
        // TODO: retrieval and iteratively gather more messages
        int number = args.get("number").exists()
                ? args.get("number").integer()
                : 50;

        MessageChannel messageChannel = event.getChannel();

        MessageHistory history = messageChannel.getHistory();
        history.retrievePast(number).queue(messageList -> {
            for (Message message : messageList) {
                message.delete().queue();
            }

            messageChannel.sendMessage("Cleaned up `" + messageList.size() + "` messages")
                    .queue(response -> response.delete().queueAfter(15, TimeUnit.SECONDS));
        });
    }
}
