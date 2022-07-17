package pl.mcsu.core.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.mcsu.core.repository.Repository;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (Repository.getInstance().getNpc().isEmpty()) return;
        if (event.getFrom().getBlockX() == event.getTo().getBlockX() &&
                event.getFrom().getBlockZ() == event.getTo().getBlockZ()) return;
        Repository.getInstance().getNpc()
                .stream()
                .filter(npc -> npc.getLocation().getChunk().equals(event.getPlayer().getLocation().getChunk()))
                .forEach(npc -> {
                    if (npc.isInZone(event.getPlayer(), 10)) npc.lookAtPlayer(event.getPlayer(), event.getPlayer());
                });
    }

}
