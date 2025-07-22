package com.tommustbe12.simpleEmojis;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SimpleEmojiCommand implements CommandExecutor {

    private final SimpleEmojis plugin;

    public SimpleEmojiCommand(SimpleEmojis plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(SimpleEmojis.getPrefix() + ChatColor.RED + "Usage: /simpleemojis <list|reload>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "list":
                sender.sendMessage(SimpleEmojis.getPrefix() + ChatColor.YELLOW + "Current Emojis:");
                sender.sendMessage(SimpleEmojis.getPrefix() + ChatColor.GRAY + "These can be used by typing the placeholder in chat.");
                plugin.getEmojiMap().forEach((key, emoji) ->
                        sender.sendMessage(ChatColor.GRAY + "  " + key + " -> " + emoji));
                break;

            case "reload":
                plugin.reloadConfig();
                plugin.loadEmojiMap();
                sender.sendMessage(SimpleEmojis.getPrefix() + ChatColor.GREEN + "Config reloaded.");
                break;

            case "help":
                sender.sendMessage("§eSimpleEmojis Help:");
                sender.sendMessage("§6/simpleemojis list §7- Lists all emojis");
                sender.sendMessage("§6/simpleemojis reload §7- Reloads the emoji configuration");
                sender.sendMessage("§6/simpleemojis help §7- Shows this help message");
                sender.sendMessage("§6/addemoji <placeholder> <emoji> §7- Add a new emoji with placeholder (e.g. :heart:) and emoji (e.g. ❤) to the config.");
                sender.sendMessage("§6/removeemoji <placeholder> §7- Choose an emoji from the config to remove.");
                break;

            default:
                sender.sendMessage(SimpleEmojis.getPrefix() + ChatColor.RED + "Unknown subcommand.");
        }
        return true;
    }
}
