package com.focamacho.seallibrary.item.lib;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

/**
 * Encantamento falso utilizado para
 * efeito de brilho em um item.
 */
public class FakeEnchantment extends Enchantment {

    public static final Enchantment FAKE_ENCHANTMENT = new FakeEnchantment(109);

    public FakeEnchantment(int id) {
        super(id);
    }

    @Override
    public @org.jetbrains.annotations.NotNull String getName() {
        return "fakeenchantment";
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public int getStartLevel() {
        return 0;
    }

    @Override
    public @org.jetbrains.annotations.NotNull EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ALL;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(@org.jetbrains.annotations.NotNull Enchantment other) {
        return false;
    }

    @Override
    public boolean canEnchantItem(@org.jetbrains.annotations.NotNull ItemStack item) {
        return true;
    }
}
