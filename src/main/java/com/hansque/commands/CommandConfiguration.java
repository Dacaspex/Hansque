package com.hansque.commands;

import com.hansque.commands.argument.Argument;

import java.util.ArrayList;
import java.util.List;

public class CommandConfiguration {

    private List<Argument> arguments;
    private String trigger;
    private String description;

    public CommandConfiguration(List<Argument> arguments, String trigger, String description) {
        this.arguments = arguments;
        this.trigger = trigger;
        this.description = description;
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

    public static class Builder {
        private List<Argument> arguments;
        private String trigger;
        private String description;

        public Builder() {
            this.arguments = new ArrayList<>();
            this.trigger = "";
            this.description = "";
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

        public CommandConfiguration build() {
            return new CommandConfiguration(arguments, trigger, description);
        }
    }
}
