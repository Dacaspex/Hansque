package com.hansque.core;

import com.hansque.commands.Command;
import com.hansque.commands.CommandConfiguration;
import com.hansque.commands.CommandStringUtil;
import com.hansque.commands.argument.Arguments;
import com.hansque.modules.Module;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.List;

/**
 * Discord bot
 */
public class Hansque {

    private JDA jda;
    private String commandPrefix;
    private List<Module> modules;
    /**
     * Mapping between moduleString -> module
     */
    private HashMap<String, Module> loadedModules;
    /**
     * Mapping between aliasString -> moduleString:triggerString
     */
    private HashMap<String, String> aliases;
    /**
     * Mapping between moduleString:triggerString -> command
     */
    private HashMap<String, Command> commands;

    public Hansque(JDA jda, String commandPrefix, List<Module> modules) {
        this.jda = jda;
        this.commandPrefix = commandPrefix;
        this.modules = modules;
        this.loadedModules = new HashMap<>();
        this.aliases = new HashMap<>();
        this.commands = new HashMap<>();
    }

    /**
     * Initialises the bot, registering all enabled modules and setting up event listeners.
     */
    public void initialise() {
        // Load all modules that are enabled
        for (Module module : modules) {
            if (module.isEnabled()) {
                module.initialise();
                registerModule(module);
            }
        }

        // Lastly, register the event listener
        jda.addEventListener(new EventListener());
    }

    /**
     * Internal function to register a module and store information about it.
     *
     * @param module Module to register
     */
    private void registerModule(Module module) {
        loadedModules.put(module.getName(), module);

        // Save commands
        for (Command command : module.getCommands()) {
            // Initialise command
            command.initialise();
            CommandConfiguration configuration = command.getConfiguration();
            String fullCommandName = module.getName() + ":" + configuration.getTrigger();

            // And store for easier access
            commands.put(fullCommandName, command);
            for (String alias : configuration.getAliases()) {
                aliases.put(alias, fullCommandName);
            }
        }
    }

    class EventListener extends ListenerAdapter {
        @Override
        public void onMessageReceived(MessageReceivedEvent event) {
            // Get message from event
            String message = event.getMessage().getContentRaw();

            // Test if the message starts with the command prefix
            if (!message.startsWith(commandPrefix)) {
                return;
            }

            // Strip this prefix for easier processing
            String commandString = CommandStringUtil.stripPrefixFromString(message, commandPrefix);

            // Convert alias to actual command
            commandString = CommandStringUtil.convertAlias(commandString, aliases);

            // Get information from command string
            String module = CommandStringUtil.getModuleFromCommandString(commandString);
            String trigger = CommandStringUtil.getTriggerFromCommandString(commandString);
            List<String> args = CommandStringUtil.getArgumentsFromCommandString(commandString);
            String commandKey = module + ":" + trigger;

            // Check if command exists
            if (!commands.containsKey(commandKey)) {
                return; // Temporary
            }

            // Get command and create objects
            Command command = commands.get(module + ":" + trigger);
            CommandConfiguration commandConfiguration = command.getConfiguration();
            Arguments arguments = new Arguments(args, commandConfiguration);

            if (!arguments.check()) {
                // TODO: Inform user that the arguments provided are not valid
                // TODO: Possible with a "usage: ..." message
                // Temporary
                event.getChannel().sendMessage(command.getConfiguration().getDescription()).queue();
            } else {
                loadedModules.get(module).execute(command, arguments, event);
            }
        }

        @Override
        public void onReady(ReadyEvent event) {
            System.out.println("Bot ready");
        }
    }
}
