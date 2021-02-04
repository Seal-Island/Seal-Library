package com.focamacho.seallibrary.item;

import com.focamacho.seallibrary.item.lib.ItemFlag;
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
        return stack.toContainer().get(DataQuery.of("UnsafeData")).toString();
    }

    @Override
    public ISealStack setData(String nbt) {
        stack.toContainer().set(DataQuery.of("UnsafeData"), nbt);
        return this;
    }

    @Override
    public ISealStack copy() {
        return SealStack.get(((ItemStack)toOriginal()).copy());
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
