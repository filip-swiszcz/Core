package pl.mcsu.core.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.mcsu.core.controller.UserController;

public class JoinListener implements Listener {

    @EventHandler
    public void join(PlayerJoinEvent event) {
        if (!UserController.getInstance().hasUser(event.getPlayer().getUniqueId()))
            UserController.getInstance().setUser(event.getPlayer().getUniqueId());


    }

}
