package pl.mcsu.core.gui.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.java.JavaPlugin;
import pl.mcsu.core.Core;
import pl.mcsu.core.gui.GUI;

public class CloseListener implements Listener {

    private final JavaPlugin owner;
    private final Core core;

    public CloseListener(JavaPlugin owner, Core core) {
        this.owner = owner;
        this.core = core;
    }

    @EventHandler
    public void close(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() == null) return;
        if (!(event.getInventory().getHolder() instanceof GUI gui)) return;
        if (!gui.getOwner().equals(owner)) return;
        if (gui.getClose() != null) gui.getClose().accept(gui);
    }

}
