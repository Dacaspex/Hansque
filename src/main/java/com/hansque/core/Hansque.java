package com.hansque.core;

import com.amihaiemil.eoyaml.YamlMapping;
import com.amihaiemil.eoyaml.YamlNode;
import com.amihaiemil.eoyaml.YamlSequence;
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

public class Hansque {

    private JDA jda;
    private String commandPrefix;
    private HashMap<String, Module> modules;
    private HashMap<String, CommandConfiguration> commandConfigurations;

    private HashMap<String, String> aliasMap;

    public Hansque(JDA jda, String commandPrefix) {
        this.jda = jda;
        this.commandPrefix = commandPrefix;
        this.modules = new HashMap<>();
        this.aliasMap = new HashMap<>();
        this.commandConfigurations = new HashMap<>();
    }

    public void initialise() {
        // Load aliases into map

        // Load all modules

        // Lastly, register the event listener
        jda.addEventListener(new EventListener());
    }

    public void registerModule(String name, Module module) {
        modules.put(name, module);

        // Save command configurations
        for (Command command : module.getCommands()) {
            CommandConfiguration configuration = command.configure();
            commandConfigurations.put(name + ":" + configuration.getTrigger(), configuration);
        }
    }

    /**
     * Loads the aliases of all registered modules
     * Should therefore be called after all modules have been registered
     *
     * @param moduleMapping The YAML mapping containing the modules
     */
    public void loadAliases(YamlMapping moduleMapping) {
        for (String moduleName : modules.keySet()) {
            if (!modules.containsKey(moduleName)) {
                // TODO: throw error regarding invalid YAML
                return;
            }
            Module module = modules.get(moduleName);
            YamlMapping commandMapping = moduleMapping.yamlMapping(moduleName).yamlMapping("commands");
            for (Command command : module.getCommands()) {
                // TODO: Configure command once, then execute 'loadAliases' when all is known about the commands
                YamlMapping commandNode = commandMapping.yamlMapping(command.configure().getTrigger());
                if (commandNode == null) {
                    // TODO: throw error regarding invalid YAML
                    continue;
                }
                YamlSequence aliasSequence = commandNode.yamlSequence("aliases");
                for (YamlNode aliasNode : aliasSequence.children()) {
                    aliasMap.put(aliasNode.toString(), moduleName + ":" + command.configure().getTrigger());
                }
            }
        }
    }

    class EventListener extends ListenerAdapter {
        @Override
        public void onMessageReceived(MessageReceivedEvent event) {

            String message = event.getMessage().getContentRaw();
            System.out.println(message);
            if (!message.startsWith(commandPrefix)) {
                // No command
                return;
            }
            message = message.substring(1);

            String[] messageParts = message.split(" ");

            // Split message on ':' and ' '
            String command = messageParts[0];
            String[] commandParts = command.split(":");
            String module = commandParts[0];
            String trigger = commandParts[1];

            // Fill arguments
            List<String> args = new ArrayList<String>();
            for (int i = 1; i < messageParts.length; i++) {
                args.add(messageParts[i]);
                System.out.println(messageParts[i]);
            }

            // Alias lookup
            // Alias cannot contain ':', might be an issue
//            if (aliasMap.containsKey(command)) {
//                String commandFromAlias = aliasMap.get(command);
//            } else {
//
//            }

            System.out.println(module);
            System.out.println(trigger);

            modules.get(module).execute(
                    trigger,
                    new Arguments(args, commandConfigurations.get(module + ":" + trigger)),
                    event
            );

            // TODO: command parametrisation and calling
            // TODO: input validation
        }

        @Override
        public void onReady(ReadyEvent event) {
            System.out.println("Bot ready");
        }
    }
}
