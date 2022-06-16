package pl.mcsu.core.gui.model;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

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
