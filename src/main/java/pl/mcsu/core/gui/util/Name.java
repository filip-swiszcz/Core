package pl.mcsu.core.gui.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;

public class Name {

    public static Component PREVIOUS = Component.text()
            .append(Component.text("Poprzednia strona"))
            .decoration(TextDecoration.ITALIC, false)
            .build();
    public static Component CURRENT = Component.text()
            .append(Component.text("Strona 1"))
            .decoration(TextDecoration.ITALIC, false)
            .build();
    public static Component NEXT = Component.text()
            .append(Component.text("Następna strona"))
            .decoration(TextDecoration.ITALIC, false)
            .build();

}
