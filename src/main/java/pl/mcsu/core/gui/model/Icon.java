package pl.mcsu.core.gui.model;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.UUID;

public class Icon {

    private final ItemStack itemStack;

    public Icon(Material material) {
        this(material, 1);
    }

    public Icon(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
    }

    public Icon displayName(Component displayName) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(displayName);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public Icon lore(Component... lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.lore(Arrays.asList(lore));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public Icon icon(String value) {
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", value));
        try {
            Field field = skullMeta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(skullMeta, profile);
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            exception.getCause();
        }
        itemStack.setItemMeta(skullMeta);
        return this;
    }

    public Icon glow() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(Enchantment.LUCK, 10, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public Icon hide() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }

}
