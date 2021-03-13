package com.focamacho.seallibrary.item;

import com.focamacho.seallibrary.item.lib.FakeEnchantment;
import com.focamacho.seallibrary.item.lib.ItemFlag;
import net.minecraft.server.v1_12_R1.Item;
import net.minecraft.server.v1_12_R1.MojangsonParseException;
import net.minecraft.server.v1_12_R1.MojangsonParser;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SealStackBukkit implements ISealStack {

    private ItemStack stack;

    public SealStackBukkit(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public Object toOriginal() {
        return stack;
    }

    @Override
    public String toJson() {
        net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);

        JSONObject json = new JSONObject();
        json.put("item", Item.REGISTRY.b(nmsStack.getItem()).toString() + (nmsStack.getData() != 0 ? ":" + nmsStack.getData() : ""));
        json.put("data", getData());
        json.put("amount", getAmount());

        return json.toString();
    }

    @Override
    public String getName() {
        ItemMeta meta = stack.getItemMeta();
        if (meta.hasDisplayName()) return meta.getDisplayName();
        if (meta.hasLocalizedName()) return meta.getLocalizedName();
        return "";
    }

    @Override
    public ISealStack setName(String name) {
        if(name.isEmpty()) name = "§r";
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        stack.setItemMeta(meta);
        return this;
    }

    @Override
    public boolean hasCustomName() {
        return stack.getItemMeta().hasDisplayName();
    }

    @Override
    public List<String> getLore() {
        return stack.getItemMeta().hasLore() ? stack.getItemMeta().getLore() : new ArrayList<>();
    }

    @Override
    public ISealStack setLore(List<String> lore) {
        ItemMeta meta = stack.getItemMeta();
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return this;
    }

    @Override
    public ISealStack setGlowing(boolean glowing) {
        ItemMeta meta = stack.getItemMeta();
        if (glowing) {
            meta.addEnchant(FakeEnchantment.FAKE_ENCHANTMENT, 0, true);
        } else {
            meta.removeEnchant(FakeEnchantment.FAKE_ENCHANTMENT);
        }
        stack.setItemMeta(meta);
        return this;
    }

    @Override
    public ISealStack setFlag(ItemFlag flag, boolean value) {
        ItemMeta meta = stack.getItemMeta();
        org.bukkit.inventory.ItemFlag bukkitFlag = getBukkitFlag(flag);
        if (meta.hasItemFlag(bukkitFlag) && !value) meta.removeItemFlags(bukkitFlag);
        else if (value) meta.addItemFlags(bukkitFlag);
        stack.setItemMeta(meta);
        return this;
    }

    @Override
    public boolean getFlag(ItemFlag flag) {
        ItemMeta meta = stack.getItemMeta();
        org.bukkit.inventory.ItemFlag bukkitFlag = getBukkitFlag(flag);
        return meta.hasItemFlag(bukkitFlag);
    }

    @Override
    public int getAmount() {
        return stack.getAmount();
    }

    @Override
    public ISealStack setAmount(int amount) {
        stack.setAmount(amount);
        return this;
    }

    @Override
    public int getMaxAmount() {
        return stack.getMaxStackSize();
    }

    @Override
    public String getData() {
        net.minecraft.server.v1_12_R1.ItemStack craftStack = CraftItemStack.asNMSCopy(stack);
        return craftStack.hasTag() ? craftStack.getTag().toString() : "";
    }

    @Override
    public ISealStack setData(String nbt) {
        net.minecraft.server.v1_12_R1.ItemStack craftStack = CraftItemStack.asNMSCopy(stack);
        try {
            craftStack.setTag(MojangsonParser.parse(nbt));
        } catch (MojangsonParseException ignored) {}
        stack = CraftItemStack.asBukkitCopy(craftStack);
        return this;
    }

    @Override
    public ISealStack copy() {
        return SealStack.get(((ItemStack)toOriginal()).clone());
    }

    @Override
    public boolean equals(ISealStack toCompare) {
        return stack.equals(toCompare.toOriginal());
    }

    @Override
    public boolean equalsIgnoreAmount(ISealStack toCompare) {
        ItemStack firstStack = stack.clone();
        firstStack.setAmount(1);
        ItemStack secondStack = (ItemStack) toCompare.copy().setAmount(1).toOriginal();
        return firstStack.equals(secondStack);
    }

    @Override
    public boolean isEmpty() {
        return this.stack.getType() == Material.AIR;
    }

    private org.bukkit.inventory.ItemFlag getBukkitFlag(ItemFlag flag) {
        switch (flag) {
            case HIDE_DESTROYS:
                return org.bukkit.inventory.ItemFlag.HIDE_DESTROYS;
            case HIDE_ENCHANTS:
                return org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS;
            case HIDE_UNBREAKABLE:
                return org.bukkit.inventory.ItemFlag.HIDE_UNBREAKABLE;
            case HIDE_PLACED_ON:
                return org.bukkit.inventory.ItemFlag.HIDE_PLACED_ON;
            case HIDE_POTION_EFFECTS:
                return org.bukkit.inventory.ItemFlag.HIDE_POTION_EFFECTS;
            default:
                return org.bukkit.inventory.ItemFlag.HIDE_ATTRIBUTES;
        }
    }

}
