package pl.mcsu.core.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.mcsu.core.model.gui.Button;
import pl.mcsu.core.model.gui.GUI;

import java.util.Objects;

public class ClickListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null) return;
        if (!(event.getInventory().getHolder() instanceof GUI gui)) return;
        event.setCancelled(true);
        if (Objects.equals(event.getCurrentItem(), gui.getBackground())) return;
        Button button = gui.getPages().get(gui.getPage()).getButton(event.getSlot());
        if (button != null && button.getClick() != null) button.getClick().click(event);
    }

}
