package com.hansque.core;

import com.hansque.modules.Module;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.HashMap;

public class Dispatcher {

    private JDA jda;
    private HashMap<String, Module> modules;

    public Dispatcher(JDA jda) {
        this.jda = jda;
        this.modules = new HashMap<>();

        this.jda.addEventListener(new EventListener());
    }

    public void registerModule(String name, Module module) {
        modules.put(name, module);
    }

    class EventListener extends ListenerAdapter {
        @Override
        public void onMessageReceived(MessageReceivedEvent event) {
            // TODO: Alias lookup
            // TODO: Validation of input (currently breaks if not in correct format...)

            String message = event.getMessage().getContentRaw();
            if (!message.startsWith("/")) {
                // No command
                return;
            }
            message = message.substring(1);

            // Split message on ':' and ' '
            String[] parts = message.split(":| ");
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
