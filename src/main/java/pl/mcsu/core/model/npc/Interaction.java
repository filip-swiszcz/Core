package pl.mcsu.core.model.npc;

import pl.mcsu.core.event.PlayerRightClickNpcEvent;

public class Interaction {

    private Click click;
    private String command;
    private boolean enable;

    public Interaction(String command, boolean enable) {
        this.command = command;
        this.enable = enable;
    }

    public Click getClick() {
        return click;
    }

    public void setClick(Click click) {
        this.click = click;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public boolean isEnabled() {
        return enable;
    }

    public void setEnabled(boolean enable) {
        this.enable = enable;
    }

    public interface Click {

        void PlayerRightClickNpc(PlayerRightClickNpcEvent event);

    }

}
