package com.hansque.commands.argument;

import com.hansque.commands.CommandConfiguration;

import java.util.List;

public class Arguments {

    private final List<String> args;
    private final String original;
    private final CommandConfiguration configuration;

    public Arguments(List<String> args, String original, CommandConfiguration configuration) {
        this.args = args;
        this.original = original;
        this.configuration = configuration;
    }

    /**
     * @return The argument list as provided by the user (words separated by space)
     */
    public List<String> getList() {
        return args;
    }

    /**
     * @return The argument string as provided by the user (not separated or transformed)
     */
    public String getOriginal() {
        return original;
    }

    /**
     * Checks whether the provided arguments correspond the the command configuration
     * and can be cast to that specific type.
     *
     * @return whether the arguments provided are valid
     */
    public boolean check() {
        List<Argument> arguments = configuration.getArguments();

        // Count if the args is between the number of required arguments and all arguments
        long requiredCount = arguments.stream().filter(a -> a.getConstraint() == Argument.Constraint.REQUIRED).count();
        if (args.size() < requiredCount || args.size() > arguments.size()) {
            return false;
        }

        // Loop over each argument from the message and check it against the positional argument type
        int i;
        for (i = 0; i < args.size(); i++) {
            try {
                switch (arguments.get(i).getType()) {
                    case INT:
                        Integer.parseInt(args.get(i));
                        break;
                    case STRING:
                    default:
                        // Arguments are always a string
                        break;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
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
}
