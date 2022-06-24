package pl.mcsu.core.model.economy;

import org.bukkit.inventory.ItemStack;
import pl.mcsu.core.model.Icon;
import pl.mcsu.core.util.Label;

public class Bag {

    private final ItemStack bag;
    private final int coins;

    public Bag() {
        this.bag = new Icon().value("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTliOTA2YjIxNTVmMTkzNzg3MDQyMzM4ZDA1Zjg0MDM5MWMwNWE2ZDNlODE2MjM5MDFiMjk2YmVlM2ZmZGQyIn19fQ==")
                .displayName(Label.BAG)
                .lore(Label.SPACE, Label.BAG_BALANCE)
                .build();
        this.coins = 1000;
    }

    public int getCoins() {
        return coins;
    }

    public ItemStack getBag() {
        return bag;
    }

}
