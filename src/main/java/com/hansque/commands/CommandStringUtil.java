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
     * @param string String to test
     * @param prefix Prefix
     * @return True if the command string starts with the prefix
     */
    public static boolean startsWithPrefix(String string, String prefix) {
        return string.startsWith(prefix);
    }

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

    public static String convertAlias(String commandString, Map<String, String> aliases) {
        String[] parts = commandString.split(" ");
        parts[0] = aliases.getOrDefault(parts[0], parts[0]);

        return String.join(" ", parts);
    }

    public static String getModuleFromCommandString(String commandString) {
        String[] parts = commandString.split(":");
        if (parts.length < 2) {
            throw new IllegalArgumentException("The command string must contain at least one colon to identify the ");
        }

        return parts[0];
    }

    public static String getTriggerFromCommandString(String commandString) {
        String[] parts = commandString.split(" ");
        String[] commandParts = parts[0].split(":");

        return commandParts[1];
    }

    public static List<String> getArgumentsFromCommandString(String commandString) {
        String[] parts = commandString.split(" ");

        // Only return the sub list starting from 1 (i.e. parts[0] = moduleString:triggerString)
        return new ArrayList<>(Arrays.asList(parts).subList(1, parts.length));
    }
}