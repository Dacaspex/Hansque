package com.hansque.core;

import com.hansque.commands.Command;
import com.hansque.commands.CommandConfiguration;
import com.hansque.commands.CommandStringUtil;
import com.hansque.commands.argument.Arguments;
import com.hansque.event.FutureEventListener;
import com.hansque.modules.Module;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.Event;
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

    /**
     * JDA instance
     */
    private JDA jda;
    /**
     * Command prefix
     */
    private String commandPrefix;
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
    /**
     * List of future event listener
     */
    private List<FutureEventListener> futureEventListeners;

    /**
     * @param jda           JDA instance
     * @param commandPrefix Command prefix
     */
    public Hansque(JDA jda, String commandPrefix) {
        this.jda = jda;
        this.commandPrefix = commandPrefix;
        this.loadedModules = new HashMap<>();
        this.aliases = new HashMap<>();
        this.commands = new HashMap<>();
        this.futureEventListeners = new ArrayList<>();
    }

    /**
     * Initialises the sub systems of the bot itself.
     */
    public void initialise() {
        jda.addEventListener(new EventListener());
    }

    /**
     * Registers and initialises a list of modules if the module is enabled.
     *
     * @param modules Modules to register
     */
    public void registerModules(Module... modules) {
        for (Module module : modules) {
            if (!module.isEnabled()) {
                continue;
            }

            module.initialise();
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
    }

    /**
     * Add a future event listener
     *
     * @param futureEventListener Future event listener
     */
    public void registerFutureEventListener(FutureEventListener futureEventListener) {
        futureEventListeners.add(futureEventListener);
    }

    class EventListener extends ListenerAdapter {

        @Override
        public void onGenericEvent(Event event) {
            for (int i = 0; i < futureEventListeners.size(); i++) {
                FutureEventListener futureEventListener = futureEventListeners.get(i);

                // Check if the event must be processed by a future event listener
                if (futureEventListener.check(event)) {
                    futureEventListener.handle(event);

                    // Check if the listener should be consumed
                    if (futureEventListener.consumes()) {
                        return;
                    }
                }

                // If the listener is done, remove it from the future event listeners array
                if (futureEventListener.isDone()) {
                    futureEventListeners.remove(futureEventListener);
                    i--;
                }
            }

            // Handle event normally
            if (event instanceof MessageReceivedEvent) {
                handleMessageReceived((MessageReceivedEvent) event);
            }
        }

        private void handleMessageReceived(MessageReceivedEvent event) {
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
