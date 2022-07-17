package pl.mcsu.core.listener;

import com.destroystokyo.paper.event.player.PlayerUseUnknownEntityEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import pl.mcsu.core.Core;
import pl.mcsu.core.event.PlayerRightClickNpcEvent;
import pl.mcsu.core.repository.Repository;

public class PlayerUseUnknownEntityListener implements Listener {

    @EventHandler
    public void onPlayerUseUnknownEntity(PlayerUseUnknownEntityEvent event) {
        if (event.isAttack()) return;
        if (event.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        if (Repository.getInstance().getNpc().isEmpty()) return;
        Repository.getInstance().getNpc()
                .stream()
                .filter(npc -> npc.getNpcId() == event.getEntityId())
                .forEach(npc -> Core.getInstance().getServer().getScheduler()
                        .scheduleSyncDelayedTask(Core.getInstance(), () -> {
                            Core.getInstance().getServer().getPluginManager()
                                    .callEvent(new PlayerRightClickNpcEvent(event.getPlayer(), npc));
                            Repository.getInstance().getClicks().put(event.getPlayer().getUniqueId(), npc);
                        }, 0));

        // Debug
        System.out.println(Repository.getInstance().getClicks().getIfPresent(event.getPlayer().getUniqueId()));

        //if (npcClickCache.getIfPresent(event.getPlayer().getUniqueId()) != null) return;
    }

}
