package pl.mcsu.core;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import pl.mcsu.core.gui.GUI;
import pl.mcsu.core.gui.model.Button;
import pl.mcsu.core.gui.model.Icon;
import pl.mcsu.core.gui.service.PageService;
import pl.mcsu.core.gui.util.Name;

public class Core {

    private final JavaPlugin plugin;

    /**
     * Sets if GUI click should be automatically
     * canceled by default. Which prevents from item(button)
     * being taken by a player.
     * */
    private boolean cancelInventoryClick = true;

    /**
     * Builds default buttons which give player access
     * to move through GUI.
     * */
    private PageService defaultButtonsBuilder = (type, inventory) -> {
        switch (type) {
            case PREVIOUS:
                if (inventory.getPage() > 0) return new Button(new Icon(Material.ARROW)
                        .displayName(Name.PREVIOUS)
                        .build()
                ).click(event -> {
                    event.setCancelled(true);
                    inventory.previousPage(event.getWhoClicked());
                });
            case CURRENT:
                return new Button(new Icon(Material.NETHER_STAR)
                        .displayName(Name.CURRENT)
                        .build()
                ).click(event -> event.setCancelled(true));
            case NEXT:
                if (inventory.getPage() < inventory.getLastPage() - 1) return new Button(new Icon(Material.ARROW)
                        .displayName(Name.NEXT)
                        .build()
                ).click(event -> {
                    event.setCancelled(true);
                    inventory.nextPage(event.getWhoClicked());
                });
            case UNASSIGNED:
            default:
                return null;
        }
    };

    /**
     * Creates an instance of Core library
     * which is not a standalone plugin.
     * */
    public Core(JavaPlugin plugin) {
        this.plugin = plugin;
        setListeners();
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    /**
     * GUI's icons click cancellation
     * */
    public boolean isCancelInventoryClick() {
        return cancelInventoryClick;
    }

    public void setCancelInventoryClick(boolean cancelInventoryClick) {
        this.cancelInventoryClick = cancelInventoryClick;
    }

    /**
     * Default GUI buttons
     * */
    public PageService getDefaultButtonsBuilder() {
        return defaultButtonsBuilder;
    }

    public void setDefaultButtonsBuilder(PageService defaultButtonsBuilder) {
        this.defaultButtonsBuilder = defaultButtonsBuilder;
    }

    /**
     * Registers listeners
     * */
    private void setListeners() {
        //plugin.getServer().getPluginManager().registerEvents();
    }

    /**
     * Creates GUI
     * */
    public GUI create(Component name, int rows) {
        return new GUI(plugin, this, name, rows);
    }

}
