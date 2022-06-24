package pl.mcsu.core.model.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class Button {

    private final ItemStack button;
    private Button.Click click;

    public Button(ItemStack itemStack) {
        this.button = itemStack;
    }

    public Button click(Button.Click click) {
        this.click = click;
        return this;
    }

    public Button.Click getClick() {
        return click;
    }

    public void setClick(Button.Click click) {
        this.click = click;
    }

    public ItemStack getButton() {
        return button;
    }

    public interface Click {

        void click(InventoryClickEvent event);

    }

}
