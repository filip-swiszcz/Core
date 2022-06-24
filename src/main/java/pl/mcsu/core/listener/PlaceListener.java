package pl.mcsu.core.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import pl.mcsu.core.model.economy.Bag;

public class PlaceListener implements Listener {

    @EventHandler
    public void place(BlockPlaceEvent event) {
        if (event.getItemInHand().isSimilar(new Bag().getBag())) event.setCancelled(true);
    }

}
