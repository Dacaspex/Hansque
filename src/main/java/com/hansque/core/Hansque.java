package com.hansque.core;

import com.hansque.commands.Command;
import com.hansque.commands.CommandConfiguration;
import com.hansque.commands.argument.Arguments;
import com.hansque.modules.Module;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Discord bot
 */
public class Hansque {

    private JDA jda;
    private String commandPrefix;
    private List<Module> modules;
    private HashMap<String, Module> loadedModules;
    private HashMap<String, CommandConfiguration> commandConfigurations;
    private HashMap<String, String> aliases;

    public Hansque(JDA jda, String commandPrefix, List<Module> modules) {
        this.jda = jda;
        this.commandPrefix = commandPrefix;
        this.modules = modules;
        this.loadedModules = new HashMap<>();
        this.aliases = new HashMap<>();
        this.commandConfigurations = new HashMap<>();
    }

    /**
     * Initialises the bot, registering all enabled modules and setting up event listeners.
     */
    public void initialise() {
        // Load all modules that are enabled
        for (Module module : modules) {
            if (module.isEnabled()) {
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

        // Save command configurations
        for (Command command : module.getCommands()) {
            // Get configuration and full command name
            CommandConfiguration configuration = command.configure();
            String fullCommandName = module.getName() + ":" + configuration.getTrigger();

            // Store required info for easier access
            commandConfigurations.put(fullCommandName, configuration);
            for (String alias : configuration.getAliases()) {
                aliases.put(alias, fullCommandName);
            }
        }
    }

    class EventListener extends ListenerAdapter {
        @Override
        public void onMessageReceived(MessageReceivedEvent event) {
            // TODO: THis is ugly AF and should be revised in the future. It works for now but
            // TODO: That's all...

            String message = event.getMessage().getContentRaw();
            System.out.println(message);
            if (!message.startsWith(commandPrefix)) {
                // No command
                return;
            }
            message = message.substring(1);

            String[] messageParts = message.split(" ");

            // Split message on ':' and ' '
            String command;
            if (aliases.containsKey(message)) {
                command = aliases.get(message);
            } else {
                command = messageParts[0];
            }
            String[] commandParts = command.split(":");
            String module = commandParts[0];
            String trigger = commandParts[1];

            // Fill arguments
            List<String> args = new ArrayList<String>();
            for (int i = 1; i < messageParts.length; i++) {
                args.add(messageParts[i]);
            }

            loadedModules.get(module).execute(
                    trigger,
                    new Arguments(args, commandConfigurations.get(module + ":" + trigger)),
                    event
            );
        }

        @Override
        public void onReady(ReadyEvent event) {
            System.out.println("Bot ready");
        }
    }
}
