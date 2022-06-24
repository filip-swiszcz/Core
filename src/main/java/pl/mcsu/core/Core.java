package pl.mcsu.core;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import pl.mcsu.core.listener.ClickListener;
import pl.mcsu.core.listener.JoinListener;
import pl.mcsu.core.listener.PlaceListener;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Core extends JavaPlugin {

    /**
     * Creates instance of Core, which provides
     * necessary features such as: GUI, Economy
     * */
    private static Core instance;

    public Core() {
        instance = this;
    }
    public static Core getInstance() {
        return instance;
    }

    /**
     * Returns instance of config, which
     * store default settings
     * */
    private FileConfiguration config;

    public @NotNull FileConfiguration getConfig() {
        return config;
    }

    /**
     * Stores retrieved settings
     * */
    private final Map<String, Object> settingsMap = new HashMap<>();

    public Map<String, Object> getSettingsMap() {
        return settingsMap;
    }

    /**
     * Registers commands and listeners.
     * Creates config if not exists
     * and retrieve settings from it
     * */
    public @Override void onEnable() {
        setCommands();
        setConfig();
        setListeners();
        setSettings();
    }

    private void setCommands() {
        try {
            Field commandMapClass = getServer().getClass().getDeclaredField("commandMap");
            commandMapClass.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapClass.get(Bukkit.getServer());
            //commandMap.register("core", new Balance("bank"));
            commandMapClass.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void setConfig() {
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            if (file.getParentFile().mkdirs()) saveResource("config.yml", false);
            saveResource("config.yml", false);
        }
        config = new YamlConfiguration();
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void setListeners() {
        getServer().getPluginManager().registerEvents(new ClickListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlaceListener(), this);
    }

    private void setSettings() {
        Object[] mysql = new Object[3];
        mysql[0] = getConfig().get("mysql.name");
        mysql[1] = getConfig().get("mysql.user");
        mysql[2] = getConfig().get("mysql.password");
        settingsMap.put("mysql", mysql);
        int coins = getConfig().getInt("coins");
        settingsMap.put("coins", coins);
        int limit = getConfig().getInt("limit");
        settingsMap.put("limit", limit);
    }

    /**
     * Saves statistics and also
     * logs important events
     * */
    public @Override void onDisable() {

    }

}
