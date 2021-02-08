package com.focamacho.seallibrary.menu;

import com.focamacho.seallibrary.menu.item.IMenuItem;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("unused")
public class MenuBukkit extends AbstractMenu {

    protected Inventory inventory;

    public MenuBukkit(Object plugin) {
        this.plugin = plugin;
    }

    @Override
    public Object get() {
        if (inventory == null) update();
        if (!MenuListener.menus.containsKey(inventory)) MenuListener.menus.put(inventory, this);
        return inventory;
    }

    @Override
    public AbstractMenu update() {
        if (inventory == null) {
            Inventory inventory = Bukkit.createInventory(null, rows * 9, title);
            this.inventory = inventory;
            MenuListener.menus.put(inventory, this);
        }

        for (int i = 0; i < inventory.getSize(); i++) {
            if (items.containsKey(i)) {
                IMenuItem item = items.get(i);
                ItemStack invItem = inventory.getItem(i);
                if (invItem == null || !invItem.equals(item.getItem().toOriginal())) inventory.setItem(i, (ItemStack) item.getItem().toOriginal());
            } else {
                inventory.clear(i);
            }
        }
        return this;
    }

}