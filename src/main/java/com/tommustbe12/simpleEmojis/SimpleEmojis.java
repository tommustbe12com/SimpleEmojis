package com.tommustbe12.simpleEmojis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class SimpleEmojis extends JavaPlugin implements Listener {

    private static final String PREFIX = ChatColor.GRAY + "[" + ChatColor.WHITE + "Simple" + ChatColor.YELLOW + "Emojis" + ChatColor.GRAY + "] ";
    private final Map<String, String> emojiMap = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadEmojiMap();
        Bukkit.getPluginManager().registerEvents(this, this);

        PluginCommand mainCmd = getCommand("simpleemojis");
        if (mainCmd != null) {
            mainCmd.setExecutor(new SimpleEmojiCommand(this));
            mainCmd.setTabCompleter(new SimpleEmojiTabCompleter(this));
        }

        PluginCommand addCmd = getCommand("addemoji");
        if (addCmd != null) {
            addCmd.setExecutor(new AddEmojiCommand(this));
            addCmd.setTabCompleter(new SimpleEmojiTabCompleter(this));
        }

        PluginCommand removeCmd = getCommand("removeemoji");
        if (removeCmd != null) {
            removeCmd.setExecutor(new RemoveEmojiCommand(this));
            removeCmd.setTabCompleter(new SimpleEmojiTabCompleter(this));
        }

        getLogger().info("§fSimple§eEmojis §fEnabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void loadEmojiMap() {
        emojiMap.clear();
        FileConfiguration config = getConfig();
        if (config.isConfigurationSection("emojis")) {
            for (String key : config.getConfigurationSection("emojis").getKeys(false)) {
                String value = config.getString("emojis." + key);
                if (value != null) {
                    emojiMap.put(key, value);
                } else {
                    getLogger().warning("Invalid emoji mapping for key: " + key);
                }
            }
        }
    }

    public void addEmoji(String placeholder, String emoji) {
        emojiMap.put(placeholder, emoji);
        getConfig().set("emojis." + placeholder, emoji);
        saveConfig();
    }

    public Map<String, String> getEmojiMap() {
        return emojiMap;
    }

    public static String getPrefix() {
        return PREFIX;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent event) {
        String rawMessage = event.getMessage();

        for (Map.Entry<String, String> entry : this.getEmojiMap().entrySet()) {
            rawMessage = rawMessage.replace(entry.getKey(), entry.getValue());
        }

        event.setMessage(rawMessage);
    }
}
