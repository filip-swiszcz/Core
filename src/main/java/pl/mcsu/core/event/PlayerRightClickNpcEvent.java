package pl.mcsu.core.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import pl.mcsu.core.model.npc.Npc;

public class PlayerRightClickNpcEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final Npc npc;
    private final ItemStack itemStack;
    private boolean cancelled;

    public PlayerRightClickNpcEvent(final Player player, final Npc npc) {
        this(player, npc, null);
    }

    public PlayerRightClickNpcEvent(final Player player, final Npc npc, final ItemStack itemStack) {
        this.player = player;
        this.npc = npc;
        this.itemStack = itemStack;
        this.cancelled = false;
    }

    public Player getPlayer() {
        return player;
    }

    public Npc getClicked() {
        return npc;
    }

    public ItemStack getClickedWith() {
        return itemStack;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }

}
