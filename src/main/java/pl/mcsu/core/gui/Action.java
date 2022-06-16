package pl.mcsu.core.gui;

public enum Action {

    PREVIOUS(1), CURRENT(2), NEXT(3), UNASSIGNED(0);

    private final int slot;

    Action(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

    public static Action forSlot(int slot) {
        for (Action action : Action.values())
            if (action.slot == slot) return action;
        return Action.UNASSIGNED;
    }

}
