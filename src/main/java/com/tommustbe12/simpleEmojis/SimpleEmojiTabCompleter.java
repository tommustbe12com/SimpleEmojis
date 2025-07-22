package com.tommustbe12.simpleEmojis;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;

public class SimpleEmojiTabCompleter implements TabCompleter {

    private final SimpleEmojis plugin;

    public SimpleEmojiTabCompleter(SimpleEmojis plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        String cmd = command.getName().toLowerCase();

        if (cmd.equals("simpleemojis")) {
            if (args.length == 1) return Arrays.asList("list", "reload", "help");
        }

        if (cmd.equals("addemoji")) {
            if (args.length == 1) return Collections.singletonList(":example:");
            if (args.length == 2) return Collections.singletonList("😀");
        }

        if (cmd.equals("removeemoji")) {
            if (args.length == 1) {
                String typed = args[0].toLowerCase();
                List<String> suggestions = new ArrayList<>();
                for (Map.Entry<String, String> entry : plugin.getEmojiMap().entrySet()) {
                    String key = entry.getKey();
                    String emoji = entry.getValue();
                    if (key.toLowerCase().startsWith(typed)) {
                        suggestions.add(key + " → " + emoji); // visual suggestion
                    }
                }
                return suggestions;
            }
        }

        return Collections.emptyList();
    }
}
