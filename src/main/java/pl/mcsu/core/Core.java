package pl.mcsu.core;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import pl.mcsu.core.gui.listener.ClickListener;

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
    private final Map<String, Object[]> settingsMap = new HashMap<>();

    public Map<String, Object[]> getSettingsMap() {
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
    }

    private void setSettings() {
        Object[] mysql = new Object[3];
        mysql[0] = getConfig().get("mysql.name");
        mysql[1] = getConfig().get("mysql.user");
        mysql[2] = getConfig().get("mysql.password");
        settingsMap.put("mysql", mysql);
        Object[] assets = new Object[4];
        assets[0] = getConfig().get("assets.coins");
        assets[1] = getConfig().get("assets.emeralds");
        assets[2] = getConfig().get("assets.netherite");
        assets[3] = getConfig().get("assets.diamonds");
        settingsMap.put("assets", assets);
    }

    /**
     * Saves statistics and also
     * logs important events
     * */
    public @Override void onDisable() {

    }


    /**
     * Sets if GUI click should be automatically
     * canceled by default. Which prevents from item(button)
     * being taken by a player.
     * */
    //private boolean cancelInventoryClick = true;

    /**
     * Builds default buttons which give player access
     * to move through GUI.
     * */
    /*private PageService defaultButtonsBuilder = (type, inventory) -> {
        switch (type) {
            case PREVIOUS:
                if (inventory.getPage() > 0) return new Button(new Item(Material.ARROW)
                        .displayName(Label.PREVIOUS)
                        .build()
                ).click(event -> {
                    event.setCancelled(true);
                    inventory.previousPage(event.getWhoClicked());
                });
            case CURRENT:
                return new Button(new Item(Material.NETHER_STAR)
                        .displayName(Label.CURRENT)
                        .build()
                ).click(event -> event.setCancelled(true));
            case NEXT:
                if (inventory.getPage() < inventory.getLastPage() - 1) return new Button(new Item(Material.ARROW)
                        .displayName(Label.NEXT)
                        .build()
                ).click(event -> {
                    event.setCancelled(true);
                    inventory.nextPage(event.getWhoClicked());
                });
            case UNASSIGNED:
            default:
                return null;
        }
    };*/

    /**
     * Creates an instance of Core library
     * which is not a standalone plugin.
     * */
    /*public Core(JavaPlugin plugin) {
        this.plugin = plugin;
        setListeners();
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }*/

    /**
     * GUI's icons click cancellation
     * */
    /*public boolean isCancelInventoryClick() {
        return cancelInventoryClick;
    }

    public void setCancelInventoryClick(boolean cancelInventoryClick) {
        this.cancelInventoryClick = cancelInventoryClick;
    }*/

    /**
     * Default GUI buttons
     * */
    /*public PageService getDefaultButtonsBuilder() {
        return defaultButtonsBuilder;
    }

    public void setDefaultButtonsBuilder(PageService defaultButtonsBuilder) {
        this.defaultButtonsBuilder = defaultButtonsBuilder;
    }*/

    /**
     * Registers listeners
     * */
    /*private void setListeners() {
        //plugin.getServer().getPluginManager().registerEvents();
    }*/

    /**
     * Creates GUI
     * */
    /*public GUI create(Component name, int rows) {
        return new GUI(plugin, this, name, rows);
    }*/

}
