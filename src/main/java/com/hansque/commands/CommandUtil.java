package com.hansque.commands;

import net.dv8tion.jda.core.entities.*;

import java.util.ArrayList;
import java.util.List;

public class CommandUtil {

    /**
     * Checks whether a message in discord has a certain command prefix
     * @param message the discord message
     * @param commandPrefix the command prefix
     * @return whether message has prefix commandPrefix
     */
    public static boolean hasCommandPrefix(Message message, String commandPrefix) {
        if (message == null || commandPrefix.isEmpty()) {
            return false;
        }
        return message.getContentRaw().startsWith(commandPrefix);
    }

    public static List<CommandArgument> getArguments(Message message) {
        if (message == null || !message.getType().equals(MessageType.DEFAULT)) {
            // TODO: throw exception when this occurs
            return null;
        }
        List<CommandArgument> arguments = new ArrayList<>();

        String content = message.getContentRaw();
        String[] parts = content.split(" ");

        // Skip first part of split, which contains the command
        for (int i = 1; i < parts.length; i++) {
            System.out.println("i: " + i + " " + parts[i]);

            if (TypeUtil.isBoolean(parts[i])) {
                boolean bool = Boolean.parseBoolean(parts[i]);
                arguments.add(new CommandArgument<>(bool));
                System.out.println("boolean");
            } else if (TypeUtil.isInteger(parts[i], 10)) {
                int integer = Integer.parseInt(parts[i]);
                arguments.add(new CommandArgument<>(integer));
                System.out.println("integer");
            } else if (TypeUtil.isDouble(parts[i])) {
                arguments.add(new CommandArgument<>(Double.valueOf(parts[i])));
                System.out.println("double");
            } else if (parts[i].startsWith("<@&")) {
                for (Role role : message.getMentionedRoles()) {
                    if (parts[i].equals("<@&" + role.getId() + ">")) {
                        arguments.add(new CommandArgument<>(role));
                        System.out.println("role");
                    }
                }
            } else if (parts[i].startsWith("<@")) {
                for (Member member : message.getMentionedMembers()) {
                    if (parts[i].equals("<@" + member.getUser().getId() + ">")) {
                        arguments.add(new CommandArgument<>(member));
                        System.out.println("member");
                    }
                }
            } else if (parts[i].startsWith("<#")) {
                for (TextChannel textChannel : message.getMentionedChannels()) {
                    if (parts[i].equals("<#" + textChannel.getId() + ">")) {
                        arguments.add(new CommandArgument<>(textChannel));
                        System.out.println("channel");
                    }
                }
            } else {
                arguments.add(new CommandArgument<>(parts[i]));
                System.out.println("string");
            }
        }

        return arguments;
    }

}
