package pl.mcsu.core.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.mcsu.core.model.npc.Interaction;
import pl.mcsu.core.event.PlayerRightClickNpcEvent;

public class PlayerRightClickNpcListener implements Listener {

    @EventHandler
    public void onPlayerRightClickNpc(PlayerRightClickNpcEvent event) {
        if (!event.getClicked().isInZone(event.getPlayer(), 10)) return;
        Interaction interaction = event.getClicked().getInteraction();
        interaction.getClick().PlayerRightClickNpc(event);
    }

}
