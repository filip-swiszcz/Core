package pl.mcsu.core.gui.model;

import java.util.ArrayList;
import java.util.List;

public class Page {

    private final int number;
    private final List<Button> buttons;
    private final List<Integer> slots;

    public Page(int number) {
        this.number = number;
        this.buttons = new ArrayList<>();
        this.slots = new ArrayList<>();
    }

    public int getNumber() {
        return number;
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public Button getButton(int slot) {
        return buttons.get(slots.indexOf(slot));
    }

    public void addButton(Button button, int slot) {
        if (slots.contains(slot)) {
            buttons.remove(slots.indexOf(slot));
            slots.remove((Integer) slot);
        }
        buttons.add(button);
        slots.add(slot);
    }

    public List<Integer> getSlots() {
        return slots;
    }

    public int getSlot(Button button) {
        return slots.indexOf(buttons.indexOf(button));
    }

}
