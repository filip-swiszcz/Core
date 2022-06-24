package pl.mcsu.core.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;

public class Label {

    /**
     * Server name label
     * */
    public static Component SERVER = Component.text()
            .append(Component.text("MCSU.PL", Color.PURPLE))
            .decoration(TextDecoration.BOLD, true)
            .decoration(TextDecoration.ITALIC, false)
            .build();

    /**
     * GUI buttons labels
     * */
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

    /**
     * Bag of coins labels
     * */
    public static Component BAG = Component.text()
            .append(Component.text("Worek monet", Color.YELLOW))
            .decoration(TextDecoration.ITALIC, false)
            .build();
    public static Component BAG_BALANCE = Component.text()
            .append(Component.text("Zawiera", Color.GRAY))
            .append(Component.text("1,000 monet", Color.YELLOW))
            .append(Component.text("!", Color.GRAY))
            .decoration(TextDecoration.ITALIC, false)
            .build();

    /**
     * Additional labels
     * */
    public static Component SPACE = Component.text()
            .append(Component.text(" "))
            .decoration(TextDecoration.ITALIC, false)
            .build();

}
