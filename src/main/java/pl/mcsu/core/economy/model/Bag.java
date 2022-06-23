package pl.mcsu.core.economy.model;

import org.bukkit.inventory.ItemStack;
import pl.mcsu.core.model.Item;

public class Bag {

    //private final  COIN BAG ICON HERE (ItemStack)
    private final ItemStack bag;
    private final int amount;

    public Bag(int amount) {
        this.bag = new Item();
        this.amount = amount;
    }

}
