package pl.mcsu.core.gui.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;

public class Label {

    public static Component SERVER = Component.text()
            .append(Component.text("MCSU.PL", Color.PURPLE))
            .decoration(TextDecoration.BOLD, true)
            .decoration(TextDecoration.ITALIC, false)
            .build();
    public static Component CLOSE = Component.text()
            .append(Component.text("Zamknij", Color.GRAY))
            .decoration(TextDecoration.ITALIC, false)
            .build();
    public static Component PREVIOUS = Component.text()
            .append(Component.text("Poprzednia strona", Color.GRAY))
            .decoration(TextDecoration.ITALIC, false)
            .build();
    public static Component NEXT = Component.text()
            .append(Component.text("Następna strona", Color.GRAY))
            .decoration(TextDecoration.ITALIC, false)
            .build();

}
