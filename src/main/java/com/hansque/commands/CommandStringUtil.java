package com.hansque.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Static utility class for common methods on command strings
 */
public class CommandStringUtil {

    /**
     * Strips a prefix from a string. The string must start with the prefix, otherwise an
     * illegal argument exception is thrown.
     *
     * @param string String to strip
     * @param prefix Prefix
     * @return Command string without prefix
     * @throws IllegalArgumentException If the string does not start with the prefix
     */
    public static String stripPrefixFromString(String string, String prefix) {
        if (!string.startsWith(prefix)) {
            throw new IllegalArgumentException("The provided string must start with prefix");
        }

        return string.substring(prefix.length());
    }

    /**
     * Converts an command alias in a comand string to the actual command name. If no alias
     * is found, the command string is not changed and returned as was given.
     *
     * @param commandString Command string in the form (module:trigger)|alias arg1 arg2 ...
     * @param aliases       Mapping between alias -> module:trigger
     * @return Command string in which the alias is replaced by the command name
     */
    public static String convertAlias(String commandString, Map<String, String> aliases) {
        String[] parts = commandString.split(" ");
        parts[0] = aliases.getOrDefault(parts[0], parts[0]);

        return String.join(" ", parts);
    }

    /**
     * Gets the module name from the command string. The command string must be of the following
     * format: module:trigger arg1 arg2 ...
     *
     * @param commandString Command string in the described format
     * @return Module name
     */
    public static String getModuleFromCommandString(String commandString) {
        String[] parts = commandString.split(":");
        if (parts.length < 2) {
            throw new IllegalArgumentException("The command string must contain at least one colon to identify the module");
        }

        return parts[0];
    }

    /**
     * Gets the trigger name from the command string. The command string must be of the following
     * format: module:trigger arg1 arg2 ...
     *
     * @param commandString Command string in the described format
     * @return Trigger name
     */
    public static String getTriggerFromCommandString(String commandString) {
        String[] parts = commandString.split(" ");
        String[] commandParts = parts[0].split(":");

        return commandParts[1];
    }

    /**
     * Gets a list of the arguments in the command string. The command string must be in the following
     * format: module:trigger arg1 arg2 ...
     *
     * @param commandString Command string in the described format
     * @return List of arguments
     */
    public static List<String> getArgumentsFromCommandString(String commandString) {
        String[] parts = commandString.split(" ");

        // Only return the sub list starting from 1 (i.e. parts[0] = moduleString:triggerString)
        return new ArrayList<>(Arrays.asList(parts).subList(1, parts.length));
    }
}
