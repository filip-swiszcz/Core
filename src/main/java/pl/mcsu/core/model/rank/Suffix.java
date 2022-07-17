package pl.mcsu.core.model.rank;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;

public class Suffix {

    private final Component suffix;
    private final String text;
    private final String color;

    public Suffix(String text, String color) {
        this.suffix = Component.literal(" " + text).withStyle(Style.EMPTY.withColor(TextColor.parseColor(color)));
        this.text = text;
        this.color = color;
    }

    public Component getSuffix() {
        return suffix;
    }

    public String getText() {
        return text;
    }

    public String getColor() {
        return color;
    }

}
