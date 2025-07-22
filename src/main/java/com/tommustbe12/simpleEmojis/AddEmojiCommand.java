package com.tommustbe12.simpleEmojis;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AddEmojiCommand implements CommandExecutor {

    private final SimpleEmojis plugin;

    public AddEmojiCommand(SimpleEmojis plugin) {
        this.plugin = plugin;
    }

    private boolean isEmoji(String input) {
        return input.codePoints().count() == 1;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("simpleemojis.add")) {
            sender.sendMessage(SimpleEmojis.getPrefix() + ChatColor.RED + "You don't have permission.");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(SimpleEmojis.getPrefix() + ChatColor.RED + "Usage: /addemoji <placeholder> <emoji>");
            return true;
        }

        String key = args[0];
        String emoji = args[1];

        if (!isEmoji(emoji)) {
            sender.sendMessage(SimpleEmojis.getPrefix() + ChatColor.RED + "Second argument must be a single emoji character.");
            return true;
        }

        plugin.addEmoji(key, emoji);
        sender.sendMessage(SimpleEmojis.getPrefix() + ChatColor.GREEN + "Added emoji: " + key + " -> " + emoji);
        return true;
    }
}
