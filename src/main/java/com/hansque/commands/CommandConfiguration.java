package com.hansque.commands;

import com.hansque.commands.argument.Argument;

import java.util.ArrayList;
import java.util.List;

public class CommandConfiguration {

    private final List<Argument> arguments;
    private final String trigger;
    private final String description;
    private final List<String> aliases;
    private final boolean parseArguments;

    public CommandConfiguration(
            List<Argument> arguments,
            String trigger,
            String description,
            List<String> aliases,
            boolean parseArguments
    ) {
        this.arguments = arguments;
        this.trigger = trigger;
        this.description = description;
        this.aliases = aliases;
        this.parseArguments = parseArguments;
    }

    public List<Argument> getArguments() {
        return arguments;
    }

    public String getTrigger() {
        return trigger;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public boolean getParseArguments() {
        return parseArguments;
    }

    public static class Builder {
        private List<Argument> arguments;
        private String trigger;
        private String description;
        private List<String> aliases;
        private boolean parseArguments;

        public Builder() {
            this.arguments = new ArrayList<>();
            this.trigger = "";
            this.description = "";
            this.aliases = new ArrayList<>();
            this.parseArguments = true;
        }

        public Builder addArgument(Argument argument) {
            arguments.add(argument);

            return this;
        }

        public Builder setTrigger(String trigger) {
            this.trigger = trigger;

            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;

            return this;
        }

        public Builder addAliases(List<String> aliases) {
            this.aliases.addAll(aliases);

            return this;
        }

        public Builder disableArgumentParsing() {
            parseArguments = false;

            return this;
        }

        public CommandConfiguration build() {
            return new CommandConfiguration(arguments, trigger, description, aliases, parseArguments);
        }
    }
}
