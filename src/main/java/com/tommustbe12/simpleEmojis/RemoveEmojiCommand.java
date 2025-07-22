package com.tommustbe12.simpleEmojis;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RemoveEmojiCommand implements CommandExecutor {

    private final SimpleEmojis plugin;

    public RemoveEmojiCommand(SimpleEmojis plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("simpleemojis.remove")) {
            sender.sendMessage(SimpleEmojis.getPrefix() + ChatColor.RED + "You don't have permission.");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(SimpleEmojis.getPrefix() + ChatColor.RED + "Usage: /removeemoji <placeholder>");
            return true;
        }

        String key = args[0];
        if (!plugin.getEmojiMap().containsKey(key)) {
            sender.sendMessage(SimpleEmojis.getPrefix() + ChatColor.RED + "Emoji placeholder not found.");
            return true;
        }

        plugin.getEmojiMap().remove(key);
        plugin.getConfig().set("emojis." + key, null);
        plugin.saveConfig();

        sender.sendMessage(SimpleEmojis.getPrefix() + ChatColor.YELLOW + "Removed emoji: " + key);
        return true;
    }
}
