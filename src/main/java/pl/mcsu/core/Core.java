package pl.mcsu.core;

import net.minecraft.world.scores.Scoreboard;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import pl.mcsu.core.command.SpawnCommand;
import pl.mcsu.core.listener.*;
import pl.mcsu.core.model.npc.Npc;
import pl.mcsu.core.repository.Repository;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

public class Core extends JavaPlugin {

    private static Core instance;
    private final File configFile = new File(getDataFolder(), "config.yml");
    private final File storageFile = new File(getDataFolder(), "storage.yml");
    private final FileConfiguration config = new YamlConfiguration();
    private final FileConfiguration storage = new YamlConfiguration();
    private final Scoreboard scoreboard = new Scoreboard();
    private String MYSQL_NAME;
    private String MYSQL_USER;
    private String MYSQL_PASSWORD;
    private int COINS_DEFAULT;
    private int COINS_LIMIT;
    private int NPC_ID;

    public Core() {
        instance = this;
    }

    public static Core getInstance() {
        return instance;
    }

    public @NotNull FileConfiguration getConfig() {
        return config;
    }

    public @NotNull FileConfiguration getStorage() {
        return storage;
    }

    public @NotNull Scoreboard getScoreboard() {
        return scoreboard;
    }

    public String getMySQLName() {
        return MYSQL_NAME;
    }

    public String getMySQLUser() {
        return MYSQL_USER;
    }

    public String getMySQLPassword() {
        return MYSQL_PASSWORD;
    }

    public int getDefaultCoins() {
        return COINS_DEFAULT;
    }

    public int getCoinsLimit() {
        return COINS_LIMIT;
    }

    public int getNpcId() {
        return NPC_ID;
    }

    public void setNpcId(int id) {
        this.NPC_ID = id;
    }

    public @Override void onEnable() {
        if (!configFile.exists()) {
            if (!configFile.getParentFile().mkdirs()) saveResource("config.yml", false);
            saveResource("config.yml", false);
        }
        if (!storageFile.exists()) {
            if (!storageFile.getParentFile().mkdirs()) saveResource("storage.yml", false);
            saveResource("storage.yml", false);
        }
        try {
            this.config.load(configFile);
            this.storage.load(storageFile);
        } catch (IOException | InvalidConfigurationException exception) {
            throw new RuntimeException(exception);
        }
        this.MYSQL_NAME = config.getString("mysql.name");
        this.MYSQL_USER = config.getString("mysql.name");
        this.MYSQL_PASSWORD = config.getString("mysql.password");
        this.COINS_DEFAULT = config.getInt("economy.coins");
        this.COINS_LIMIT = config.getInt("economy.limit");
        this.NPC_ID = storage.getInt("id");
        Objects.requireNonNull(storage.getConfigurationSection("npc")).getKeys(false)
                .forEach(id -> {
                    String name = storage.getString("npc." + id + ".name");
                    Location location = storage.getLocation("npc." + id + ".location");
                    Npc npc = new Npc(name, location);
                    npc.setId(Integer.parseInt(id));
                    String skin = storage.getString("npc." + id + ".skin");
                    if (skin != null) npc.skin(skin);
                    String prefixText = storage.getString("npc." + id + ".prefix.text");
                    String prefixColor = storage.getString("npc." + id + ".prefix.color");
                    if (prefixText != null && prefixColor != null) npc.prefix(prefixText, prefixColor);
                    String suffixText = storage.getString("npc." + id + ".suffix.text");
                    String suffixColor = storage.getString("npc." + id + ".suffix.color");
                    if (suffixText != null && suffixColor != null) npc.suffix(suffixText, suffixColor);
                    boolean enable = storage.getBoolean("npc." + id + ".interaction.enable");
                    String command = storage.getString("npc." + id + ".interaction.command");
                    if (enable && command != null) npc.interaction(command, true);
                    Repository.getInstance().getNpc().add(npc);
                });
        try {
            Field commandMapClass = getServer().getClass().getDeclaredField("commandMap");
            commandMapClass.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapClass.get(Bukkit.getServer());
            commandMap.register("core", new SpawnCommand("npc"));
            commandMapClass.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            throw new RuntimeException(exception);
        }
        Arrays.asList(
                new BlockPlaceListener(),
                new InventoryClickListener(),
                new PaperServerListPingListener(),
                new PlayerJoinListener(),
                new PlayerMoveListener(),
                new PlayerRightClickNpcListener(),
                new PlayerUseUnknownEntityListener()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    public @Override void onDisable() {
        if (Repository.getInstance().getNpc().isEmpty()) return;
        Repository.getInstance().getNpc()
                .forEach(npc -> {
                    storage.set("npc." + npc.getId() + ".name", npc.getName());
                    storage.set("npc." + npc.getId() + ".location", npc.getLocation());
                    storage.set("npc." + npc.getId() + ".skin", npc.getSkin().getName());
                    storage.set("npc." + npc.getId() + ".prefix.text", npc.getPrefix().getText());
                    storage.set("npc." + npc.getId() + ".prefix.color", npc.getPrefix().getColor());
                    storage.set("npc." + npc.getId() + ".suffix.text", npc.getSuffix().getText());
                    storage.set("npc." + npc.getId() + ".suffix.color", npc.getSuffix().getColor());
                    storage.set("npc." + npc.getId() + ".interaction.enable", npc.getInteraction().isEnabled());
                    storage.set("npc." + npc.getId() + ".interaction.command", npc.getInteraction().getCommand());
                });
        storage.set("id", NPC_ID);
        try {
            storage.save(storageFile);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        // delete all npc(???)
    }

}
