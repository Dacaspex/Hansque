package com.hansque.core;

import com.amihaiemil.eoyaml.YamlMapping;
import com.amihaiemil.eoyaml.YamlNode;
import com.amihaiemil.eoyaml.YamlSequence;
import com.hansque.commands.Command;
import com.hansque.modules.Module;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.HashMap;

public class Dispatcher {

    private JDA jda;
    private String commandPrefix;
    private HashMap<String, Module> modules;

    private HashMap<String, String> aliasMap;

    public Dispatcher(JDA jda, String commandPrefix) {
        this.jda = jda;
        this.commandPrefix = commandPrefix;
        this.modules = new HashMap<>();
        this.aliasMap = new HashMap<>();

        this.jda.addEventListener(new EventListener());
    }

    public void registerModule(String name, Module module) {
        modules.put(name, module);
    }

    /**
     * Loads the aliases of all registered modules
     * Should therefore be called after all modules have been registered
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
                YamlMapping commandNode = commandMapping.yamlMapping(command.getTrigger());
                if (commandNode == null) {
                    // TODO: throw error regarding invalid YAML
                    continue;
                }
                YamlSequence aliasSequence = commandNode.yamlSequence("aliases");
                for (YamlNode aliasNode : aliasSequence.children()) {
                    aliasMap.put(aliasNode.toString(), moduleName + ":" + command.getTrigger());
                }
            }
        }
    }

    class EventListener extends ListenerAdapter {
        @Override
        public void onMessageReceived(MessageReceivedEvent event) {
            // TODO: Validation of input (currently breaks if not in correct format...)

            String message = event.getMessage().getContentRaw();
            if (!message.startsWith(commandPrefix)) {
                // No command
                return;
            }
            message = message.substring(1);

            // Split message on ':' and ' '
            String[] parts = message.split("[: ]");

            // Alias lookup
            // Alias cannot contain ':', might be an issue
            if (aliasMap.containsKey(parts[0])) {
                String commandFromAlias = aliasMap.get(parts[0]);
                parts = message.replaceFirst(parts[0], commandFromAlias).split("[: ]");
            }

            if (parts.length < 2) {
                // No valid command
                return;
            }

            String module = parts[0];
            String command = parts[1];

            String[] args = new String[]{};

            if (parts.length > 2) {
                args = Arrays.copyOfRange(parts, 2, parts.length);
            }

            if (modules.containsKey(module)) {
                modules.get(module).execute(command, args, event);
            }
        }

        @Override
        public void onReady(ReadyEvent event) {
            System.out.println("Bot ready");
        }
    }
}
