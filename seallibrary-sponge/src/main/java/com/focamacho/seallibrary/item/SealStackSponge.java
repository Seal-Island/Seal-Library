package com.focamacho.seallibrary.item;

import com.focamacho.seallibrary.forge.ForgeUtils;
import com.focamacho.seallibrary.item.lib.ItemFlag;
import com.focamacho.seallibrary.nbt.ISealNBT;
import com.focamacho.seallibrary.nbt.SealNBT;
import com.focamacho.seallibrary.nbt.SealNBTSponge;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import org.json.JSONObject;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SealStackSponge implements ISealStack {

    private ItemStack stack;

    public SealStackSponge(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public Object toOriginal() {
        return stack;
    }

    @Override
    public String toJson() {
        JSONObject json = new JSONObject();

        json.put("item", stack.getType().getId() + (stack.toContainer().get(DataQuery.of("UnsafeDamage")).isPresent() ? ":" + stack.toContainer().get(DataQuery.of("UnsafeDamage")).get() : ""));
        json.put("data", getData());
        json.put("amount", getAmount());

        return json.toString();
    }

    @Override
    public String getName() {
        Optional<Text> displayName = stack.get(Keys.DISPLAY_NAME);
        return displayName.map(Text::toPlainSingle).orElseGet(() -> stack.getTranslation().get());
    }

    @Override
    public ISealStack setName(String name) {
        stack.offer(Keys.DISPLAY_NAME, Text.of(name));
        return this;
    }

    @Override
    public boolean hasCustomName() {
        return stack.get(Keys.DISPLAY_NAME).isPresent();
    }

    @Override
    @SuppressWarnings("all")
    public String getRegistryName() {
        return ((net.minecraft.item.ItemStack) ForgeUtils.getForgeStack(stack)).getItem().getRegistryName().toString();
    }

    @Override
    public int getMeta() {
        return ((net.minecraft.item.ItemStack) ForgeUtils.getForgeStack(stack)).getMetadata();
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        Optional<List<Text>> optional = stack.get(Keys.ITEM_LORE);
        optional.ifPresent(texts -> texts.forEach(text -> lore.add(text.toPlainSingle())));
        return lore;
    }

    @Override
    public ISealStack setLore(List<String> lore) {
        List<Text> text = new ArrayList<>();
        lore.forEach(string -> text.add(Text.of(string)));
        stack.offer(Keys.ITEM_LORE, text);
        return this;
    }

    @Override
    public ISealStack setGlowing(boolean glowing) {
        stack.offer(Keys.GLOWING, glowing);
        return this;
    }

    @Override
    public ISealStack setFlag(ItemFlag flag, boolean value) {
        stack.offer(getSpongeFlag(flag), value);
        return this;
    }

    @Override
    public boolean getFlag(ItemFlag flag) {
        Optional<Boolean> value = stack.get(getSpongeFlag(flag));
        return value.isPresent() && value.get();
    }

    @Override
    public int getAmount() {
        return stack.getQuantity();
    }

    @Override
    public ISealStack setAmount(int amount) {
        stack.setQuantity(amount);
        return this;
    }

    @Override
    public int getMaxAmount() {
        return stack.getMaxStackQuantity();
    }

    @Override
    public String getData() {
        net.minecraft.item.ItemStack item = (net.minecraft.item.ItemStack) ForgeUtils.getForgeStack(stack);
        if(item.getTagCompound() != null) return item.getTagCompound().toString();
        return "{}";
    }

    @Override
    public ISealStack setData(String nbt) {
        net.minecraft.item.ItemStack item = (net.minecraft.item.ItemStack) ForgeUtils.getForgeStack(stack);
        try {
            item.setTagCompound(JsonToNBT.getTagFromJson(nbt));
        } catch(Exception ignored) {}
        stack = (ItemStack) ForgeUtils.getServerStack(item).toOriginal();
        return this;
    }

    @Override
    public ISealNBT getNBT() {
        net.minecraft.item.ItemStack item = (net.minecraft.item.ItemStack) ForgeUtils.getForgeStack(stack);
        return item.hasTagCompound() ? SealNBT.get(item.getTagCompound()) : SealNBT.get(new NBTTagCompound());
    }

    @Override
    public ISealStack setNBT(ISealNBT nbt) {
        net.minecraft.item.ItemStack item = (net.minecraft.item.ItemStack) ForgeUtils.getForgeStack(stack);
        try {
            item.setTagCompound((NBTTagCompound) nbt.toOriginal());
        } catch(Exception ignored) {}
        stack = (ItemStack) ForgeUtils.getServerStack(item).toOriginal();
        return this;
    }

    @Override
    public ISealStack copy() {
        return SealStack.get(((ItemStack)toOriginal()).copy());
    }

    @Override
    public boolean equals(ISealStack toCompare) {
        return stack.equalTo((ItemStack) toCompare.toOriginal());
    }

    @Override
    public boolean equalsIgnoreAmount(ISealStack toCompare) {
        ItemStack firstStack = stack.copy();
        firstStack.setQuantity(1);
        ItemStack secondStack = ((ItemStack) toCompare.toOriginal()).copy();
        secondStack.setQuantity(1);
        return firstStack.equalTo(secondStack);
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    private Key<Value<Boolean>> getSpongeFlag(ItemFlag flag) {
        switch (flag) {
            case HIDE_DESTROYS:
                return Keys.HIDE_CAN_DESTROY;
            case HIDE_ENCHANTS:
                return Keys.HIDE_ENCHANTMENTS;
            case HIDE_UNBREAKABLE:
                return Keys.HIDE_UNBREAKABLE;
            case HIDE_PLACED_ON:
                return Keys.HIDE_CAN_PLACE;
            case HIDE_POTION_EFFECTS:
                return Keys.HIDE_MISCELLANEOUS;
            default:
                return Keys.HIDE_ATTRIBUTES;
        }
    }

}
