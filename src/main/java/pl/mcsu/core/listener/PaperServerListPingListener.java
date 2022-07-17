package pl.mcsu.core.listener;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.mcsu.core.util.Color;

import java.util.concurrent.ThreadLocalRandom;

public class PaperServerListPingListener implements Listener {

    @EventHandler
    public void onPaperServerListPing(PaperServerListPingEvent event) {
        Component message = Component.text()
                .append(Component.text("Wersja", Color.PURPLE))
                .append(Component.text(" - ", Color.GRAY))
                .append(Component.text("1.0.5", Color.GRAY))
                .build();
        event.motd(message);
        int i = ThreadLocalRandom.current().nextInt(0, 2);
        event.setNumPlayers(i);
        event.setMaxPlayers(500);
    }

}
