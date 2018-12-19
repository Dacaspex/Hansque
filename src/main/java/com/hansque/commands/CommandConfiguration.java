package com.hansque.commands;

import com.hansque.commands.argument.Argument;

import java.util.ArrayList;
import java.util.List;

public class CommandConfiguration {

    private List<Argument> arguments;
    private String trigger;
    private String description;
    private List<String> aliases;

    public CommandConfiguration(List<Argument> arguments, String trigger, String description, List<String> aliases) {
        this.arguments = arguments;
        this.trigger = trigger;
        this.description = description;
        this.aliases = aliases;
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

    public static class Builder {
        private List<Argument> arguments;
        private String trigger;
        private String description;
        private List<String> aliases;

        public Builder() {
            this.arguments = new ArrayList<>();
            this.trigger = "";
            this.description = "";
            this.aliases = new ArrayList<>();
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

        public CommandConfiguration build() {
            return new CommandConfiguration(arguments, trigger, description, aliases);
        }
    }
}
