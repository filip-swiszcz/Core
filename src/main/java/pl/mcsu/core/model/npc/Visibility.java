package pl.mcsu.core.model.npc;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;

public class Visibility {

    private final Player owner;
    private boolean everyone;
    private Collection<Player> viewers;

    public Visibility(Player player, boolean everyone) {
        this.owner = player;
        this.everyone = everyone;
        this.viewers = new ArrayList<>();
    }

    public Player getOwner() {
        return owner;
    }

    public boolean isEveryone() {
        return everyone;
    }

    public void setEveryone(boolean everyone) {
        this.everyone = everyone;
    }

    public Collection<Player> getViewers() {
        return viewers;
    }

    public void setViewers(Collection<Player> viewers) {
        this.viewers = viewers;
    }

    public boolean hasViewer(Player player) {
        return viewers.contains(player);
    }

    public void addViewer(Player player) {
        this.viewers.add(player);
    }

}
