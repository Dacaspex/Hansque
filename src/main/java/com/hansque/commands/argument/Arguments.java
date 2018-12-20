package com.hansque.commands.argument;

import com.hansque.commands.CommandConfiguration;
import com.hansque.commands.TypeUtil;

import java.util.List;

public class Arguments {

    private List<String> args;
    private CommandConfiguration configuration;

    public Arguments(List<String> args, CommandConfiguration configuration) {
        this.args = args;
        this.configuration = configuration;
    }

    /**
     * Checks whether the provided arguments correspond the the command configuration
     * @return whether the arguments provided are valid
     */
    public boolean check() {
        // Get argument list of command configuration
        List<Argument> arguments = configuration.getArguments();
        int cmdArgIndex = 0;

        // Loop over provided arguments
        for (int i = 0; i < this.args.size(); i++) {

            String providedArg = this.args.get(i);
            Argument cmdArg = arguments.get(cmdArgIndex);
            Argument.Type cmdArgType = cmdArg.getType();

            boolean argumentsMatch = false;
            // Check whether specified argument corresponds to type
            switch (cmdArgType) {
                case INT:
                    argumentsMatch = TypeUtil.isInteger(providedArg, 10);
                    break;
                case STRING:
                    // Argument is always string
                    argumentsMatch = true;
                    break;
            }

            if (!argumentsMatch) {
                if (cmdArg.getConstraint().equals(Argument.Constraint.OPTIONAL)) {
                    i--;
                } else if (cmdArg.getConstraint().equals(Argument.Constraint.REQUIRED)) {
                    return false;
                }
            }
            cmdArgIndex++;
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

    // arguments.get("city").string()
    // arguments.get("height").int()
    // arguments.get(cityArgument)
}
