package pl.mcsu.core.model.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import pl.mcsu.core.model.Item;
import pl.mcsu.core.util.Label;
import pl.mcsu.core.model.Icon;

import java.util.ArrayList;
import java.util.List;

public class GUI implements InventoryHolder {

    private Component title;
    private final List<Page> pages;
    private int page;
    private final ItemStack background;
    private final Button close;
    private final Button previous;
    private final Button next;

    public GUI(Component title) {
        this.title = title;
        this.pages = new ArrayList<>();
        this.page = 0;
        this.background = new Item(Material.GRAY_STAINED_GLASS_PANE).displayName(Label.SERVER).build();
        this.close = new Button(new Icon().value("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTI4OWQ1YjE3ODYyNmVhMjNkMGIwYzNkMmRmNWMwODVlODM3NTA1NmJmNjg1YjVlZDViYjQ3N2ZlODQ3MmQ5NCJ9fX0=")
                .displayName(Label.CLOSE).build())
                .click((InventoryClickEvent event) -> {event.getWhoClicked().closeInventory();});
        this.previous = new Button(new Icon().value("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODY5NzFkZDg4MWRiYWY0ZmQ2YmNhYTkzNjE0NDkzYzYxMmY4Njk2NDFlZDU5ZDFjOTM2M2EzNjY2YTVmYTYifX19")
                .displayName(Label.PREVIOUS).build())
                .click((InventoryClickEvent event) -> {previousPage(event.getWhoClicked());});
        this.next = new Button(new Icon().value("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjMyY2E2NjA1NmI3Mjg2M2U5OGY3ZjMyYmQ3ZDk0YzdhMGQ3OTZhZjY5MWM5YWMzYTkxMzYzMzEzNTIyODhmOSJ9fX0=")
                .displayName(Label.NEXT).build())
                .click((InventoryClickEvent event) -> {nextPage(event.getWhoClicked());});
    }

    public Component getTitle() {
        return title;
    }

    public void setTitle(Component title) {
        this.title = title;
    }

    public List<Page> getPages() {
        return pages;
    }

    public Page getPage(int number) {
        return pages.get(number - 1);
    }

    public void setPage(Page page, int number) {
        if (pages.isEmpty()) addPage(page);
        if (number > (pages.size() - 1)) addPage(page);
        pages.remove(number - 1);
        pages.add(number - 1, page);
    }

    public void addPage(Page page) {
        pages.add(page);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void previousPage(HumanEntity viewer) {
        if (page <= 0) return;
        this.page = page - 1;
        refreshInventory(viewer);
    }

    public void nextPage(HumanEntity viewer) {
        if (page >= (pages.size() - 1)) return;
        this.page = page + 1;
        refreshInventory(viewer);
    }

    public ItemStack getBackground() {
        return background;
    }

    public void refreshInventory(HumanEntity viewer) {
        if (!(viewer.getOpenInventory().getTopInventory().getHolder() instanceof GUI)
                || viewer.getOpenInventory().getTopInventory().getHolder() != this) return;
        for (int i = 0; i < 45; i++)
            viewer.getOpenInventory().getTopInventory().setItem(i, background);
        for (int i = 0; i < pages.get(page).getButtons().size(); i++)
            viewer.getOpenInventory().getTopInventory().setItem(pages.get(page).getSlots().get(i),
                    pages.get(page).getButtons().get(i).getButton());
        if (pages.size() > 1) {
            if (page == 0) {
                viewer.getOpenInventory().getTopInventory().setItem(40, close.getButton());
                viewer.getOpenInventory().getTopInventory().setItem(41, next.getButton());
                pages.get(page).addButton(close, 40);
                pages.get(page).addButton(next, 41);
                return;
            }
            if (page == pages.size() - 1) {
                viewer.getOpenInventory().getTopInventory().setItem(39, previous.getButton());
                viewer.getOpenInventory().getTopInventory().setItem(40, close.getButton());
                pages.get(page).addButton(previous, 39);
                pages.get(page).addButton(close, 40);
                return;
            }
            viewer.getOpenInventory().getTopInventory().setItem(39, previous.getButton());
            viewer.getOpenInventory().getTopInventory().setItem(40, close.getButton());
            viewer.getOpenInventory().getTopInventory().setItem(41, next.getButton());
            pages.get(page).addButton(previous, 39);
            pages.get(page).addButton(close, 40);
            pages.get(page).addButton(next, 41);
            return;
        }
        viewer.getOpenInventory().getTopInventory().setItem(40, close.getButton());
        pages.get(page).addButton(close, 40);
    }

    public @Override @NotNull Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 45, title);
        for (int i = 0; i < 45; i++)
            inventory.setItem(i, background);
        for (int i = 0; i < pages.get(page).getButtons().size(); i++)
            inventory.setItem(pages.get(page).getSlots().get(i),
                    pages.get(page).getButtons().get(i).getButton());
        if (pages.size() > 1) {
            if (page == 0) {
                inventory.setItem(40, close.getButton());
                inventory.setItem(41, next.getButton());
                pages.get(page).addButton(close, 40);
                pages.get(page).addButton(next, 41);
                return inventory;
            }
            if (page == pages.size() - 1) {
                inventory.setItem(39, previous.getButton());
                inventory.setItem(40, close.getButton());
                pages.get(page).addButton(previous, 39);
                pages.get(page).addButton(close, 40);
                return inventory;
            }
            inventory.setItem(39, previous.getButton());
            inventory.setItem(40, close.getButton());
            inventory.setItem(41, next.getButton());
            pages.get(page).addButton(previous, 39);
            pages.get(page).addButton(close, 40);
            pages.get(page).addButton(next, 41);
            return inventory;
        }
        inventory.setItem(40, close.getButton());
        pages.get(page).addButton(close, 40);
        return inventory;
    }

}
