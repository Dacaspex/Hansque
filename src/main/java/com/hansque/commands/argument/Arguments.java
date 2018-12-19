package com.hansque.commands.argument;

import com.hansque.commands.CommandConfiguration;

import java.util.List;

public class Arguments {

    private List<String> args;
    private CommandConfiguration configuration;

    public Arguments(List<String> args, CommandConfiguration configuration) {
        this.args = args;
        this.configuration = configuration;
    }

    public ArgumentValue get(String name) {
        // Search for the argument in the argument list provided by the configuration
        List<Argument> arguments = configuration.getArguments();
        int index = -1;
        for (int i = 0; i < arguments.size(); i++) {
            if (arguments.get(i).getName().equals(name)) {
                index = i;
            }
        }

        // No argument matched with the name, return null
        if (index == -1) {
            // TODO: Or throw exception that the argument was not defined?
            return new ArgumentValue(null);
        }

        // Not enough arguments supplied
        if (index > args.size() - 1) {
            return new ArgumentValue(null);
        }

        return new ArgumentValue(args.get(index));
    }

    public class ArgumentValue {
        private String value;

        public ArgumentValue(String value) {
            this.value = value;
        }

        public String string() {
            return value;
        }

        public int integer() {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                // TODO: Throw error to developer since you should have known what the type is!
                throw new RuntimeException("Temporary exception");
            }
        }

        public boolean exists() {
            return value != null;
        }
    }

    // arguments.get("city").string()
    // arguments.get("height").int()
    // arguments.get(cityArgument)
}
