package pl.mcsu.core.model.rank;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;

public class Prefix {

    private final Component prefix;
    private final String text;
    private final String color;

    public Prefix(String text, String color) {
        this.prefix = Component.literal(text + " ").withStyle(Style.EMPTY.withColor(TextColor.parseColor(color)));
        this.text = text;
        this.color = color;
    }

    public Component getPrefix() {
        return prefix;
    }

    public String getText() {
        return text;
    }

    public String getColor() {
        return color;
    }

}
