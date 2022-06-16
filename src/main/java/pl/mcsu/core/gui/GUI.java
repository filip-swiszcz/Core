package pl.mcsu.core.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import pl.mcsu.core.Core;
import pl.mcsu.core.gui.model.Button;
import pl.mcsu.core.gui.service.PageService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class GUI implements InventoryHolder {

    private final JavaPlugin owner;
    private final Core core;

    private Component title;
    private int rows;

    private int page;

    private final Map<Integer, Button> buttons;
    private final Set<Integer> sticks;

    private Consumer<GUI> change;
    private Consumer<GUI> close;

    private boolean cancelInventoryClick;

    public GUI(JavaPlugin owner, Core core, Component title, int rows) {
        this.owner = owner;
        this.core = core;
        this.title = title;
        this.rows = rows;
        this.page = 0;
        this.buttons = new HashMap<>();
        this.sticks = new HashSet<>();
    }

    /**
     * Owner
     * */

    public JavaPlugin getOwner() {
        return owner;
    }

    public Core getCore() {
        return core;
    }

    /**
     * Inventory title
     * */

    public Component getTitle() {
        return title;
    }

    public void setTitle(Component title) {
        this.title = title;
    }

    /**
     * Inventory rows
     * */

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * Inventory page
     * */

    public boolean previousPage(HumanEntity viewer) {
        if (page > 0) {
            page--;
            refresh(viewer);
            if (change != null) change.accept(this);
            return true;
        }
        return false;
    }

    public boolean nextPage(HumanEntity viewer) {
        if (page < getLastPage() - 1) {
            page++;
            refresh(viewer);
            if (change != null) change.accept(this);
            return true;
        }
        return false;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLastPage() {
        return (int) Math.ceil(((double) getLastFilledSlot() + 1) / ((double) getRows() * 9));
    }

    public int getLastFilledSlot() {
        int slot = 0;
        for(int next : buttons.keySet())
            if(buttons.get(next) != null && next > slot)
                slot = next;
        return slot;
    }

    /**
     * Inventory buttons
     * */

    public void addButton(Button button) {
        if (getLastFilledSlot() == 0 && getButton(0) == null) {
            setButton(button, 0);
            return;
        }
        setButton(button, getLastFilledSlot() + 1);
    }

    public void removeButton(int slot) {
        buttons.remove(slot);
    }

    public void removeButton(int page, int slot) {
        if (slot < 0 || slot > (getRows() * 9)) return;
        removeButton((page * (getRows() * 9)) + slot);
    }

    public Button getButton(int slot) {
        if (slot < 0 || slot > getLastFilledSlot()) return null;
        return buttons.get(slot);
    }

    public Button getButton(int page, int slot) {
        if (slot < 0 || slot > (getRows() * 9)) return null;
        return getButton((page * (getRows() * 9)) + slot);
    }

    public void setButton(Button button, int slot) {
        buttons.put(slot, button);
    }

    public void setButton(Button button, int page, int slot) {
        if (slot < 0 || slot > (getRows() * 9)) return;
        setButton(button, (page * (getPage() * 9)) + slot);
    }

    public Map<Integer, Button> getButtons() {
        return buttons;
    }

    public void setButtons(Button... buttons) {
        for (Button button : buttons) addButton(button);
    }

    /**
     * Inventory stick buttons
     * */

    public void addStick(int slot) {
        if (slot < 0 || slot >= (getRows() * 9)) return;
        sticks.add(slot);
    }

    public void removeStick(int slot) {
        sticks.remove(slot);
    }

    public boolean isStick(int slot) {
        if (slot < 0 || slot >= (getRows() * 9)) return false;
        return sticks.contains(slot);
    }

    public Set<Integer> getSticks() {
        return sticks;
    }

    public void clearAllButSticks() {
        this.page = 0;
        buttons.entrySet().removeIf(button -> !isStick(button.getKey()));
    }

    public void clearSticks() {
        sticks.clear();
    }

    /**
     * Automatic inventory click cancellation
     * */

    public boolean isCancelInventoryClick() {
        return cancelInventoryClick;
    }

    public void setCancelInventoryClick(boolean cancelInventoryClick) {
        this.cancelInventoryClick = cancelInventoryClick;
    }

    /**
     * Consumer actions
     * */

    public Consumer<GUI> getChange() {
        return change;
    }

    public Consumer<GUI> getClose() {
        return close;
    }

    /**
     * Inventory builder
     * */

    public void refresh(HumanEntity viewer) {
        if (!(viewer.getOpenInventory().getTopInventory().getHolder() instanceof GUI)
                || viewer.getOpenInventory().getTopInventory().getHolder() != this) return;
        if (viewer.getOpenInventory().getTopInventory().getSize() != (getRows() * 9) + (getLastPage() > 0 ? 9 : 0)) {
            viewer.openInventory(getInventory());
            return;
        }
        viewer.getOpenInventory().getTopInventory().setContents(getInventory().getContents());
    }

    @Override
    public @NotNull Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this,
                ((getLastPage() > 0)
                        ? (getRows() * 9) + 9
                        : (getRows() * 9)), title
        );
        for (int slot = page * (getRows() * 9); slot < (page + 1) * (getRows() * 9); slot++) {
            if (slot > getLastFilledSlot()) break;
            if (buttons.containsKey(slot))
                inventory.setItem(slot - (page * (getRows() * 9)), buttons.get(slot).getButton());
        }
        for (int stick : sticks) inventory.setItem(stick, buttons.get(stick).getButton());
        if (getLastPage() > 0) {
            PageService builder = core.getDefaultButtonsBuilder();
            for (int i = (getRows() * 9); i < ((getRows() * 9) + 9); i++) {
                int offset = i - (getRows() * 9);
                Button button = builder.builder(Action.forSlot(offset), this);
                inventory.setItem(i, button != null ? button.getButton() : null);
            }
        }
        return inventory;
    }

}
