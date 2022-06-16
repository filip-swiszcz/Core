package pl.mcsu.core.gui.model;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class Button {

    private final ItemStack button;
    private Click click;

    public Button(ItemStack itemStack) {
        this.button = itemStack;
    }

    public Button click(Click click) {
        this.click = click;
        return this;
    }

    public Click getClick() {
        return click;
    }

    public void setClick(Click click) {
        this.click = click;
    }

    public ItemStack getButton() {
        return button;
    }

    public interface Click {

        void click(InventoryClickEvent event);

    }

}
