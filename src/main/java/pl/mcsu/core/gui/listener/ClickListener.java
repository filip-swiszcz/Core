package pl.mcsu.core.gui.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;
import pl.mcsu.core.Core;
import pl.mcsu.core.gui.Action;
import pl.mcsu.core.gui.GUI;
import pl.mcsu.core.gui.model.Button;
import pl.mcsu.core.gui.service.PageService;

public class ClickListener implements Listener {

    private final JavaPlugin owner;
    private final Core core;

    public ClickListener(JavaPlugin owner, Core core) {
        this.owner = owner;
        this.core = core;
    }

    @EventHandler
    public void click(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null) return;
        if (!(event.getInventory().getHolder() instanceof GUI gui)) return;
        if (!gui.getOwner().equals(owner)) return;
        if (gui.isCancelInventoryClick()) event.setCancelled(true);
        if (event.getSlot() > (gui.getRows() * 9)) {
            int offset = event.getSlot() - (gui.getRows() * 9);
            PageService builder = core.getDefaultButtonsBuilder();
            Action action = Action.forSlot(offset);
            Button button = builder.builder(action, gui);
            if (button != null) button.getClick().click(event);
            return;
        }
        if (gui.isStick(event.getSlot())) {
            Button button = gui.getButton(0, event.getSlot());
            if (button != null && button.getClick() != null)
                button.getClick().click(event);
            return;
        }
        Button button = gui.getButton(gui.getPage(), event.getSlot());
        if (button != null && button.getClick() != null)
            button.getClick().click(event);
    }

}
