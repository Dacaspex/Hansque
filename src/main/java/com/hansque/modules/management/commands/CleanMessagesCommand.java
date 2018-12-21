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
        final int number = args.get("number").exists()
                ? args.get("number").integer()
                : 50;

        final MessageChannel messageChannel = event.getChannel();

        final MessageHistory history = messageChannel.getHistory();

        new Thread(() -> {
            int toDelete = number;
            // Start at -1 since it will always clean up
            // the command trigger
            int deleted = -1;

            boolean isDeleting = true;
            while (isDeleting) {

                int toRetrieve = Math.min(toDelete, 100);

                List<Message> messages = history.retrievePast(toRetrieve).complete();
                messages.forEach(msg -> msg.delete().complete());

                toDelete -= toRetrieve;
                deleted += messages.size();

                if (toDelete == 0) {
                    isDeleting = false;
                    messageChannel.sendMessage("Cleaned up `" + deleted + "` messages")
                            .queue(response -> response.delete().queueAfter(15, TimeUnit.SECONDS));
                }
            }
        }).start();
    }
}
