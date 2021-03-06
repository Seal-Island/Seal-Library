package com.focamacho.seallibrary.menu;

import com.focamacho.seallibrary.menu.item.IMenuItem;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class MenuBukkit extends Menu {

    protected Inventory inventory;

    @Override
    public Map<Integer, Object> getOriginalItems() {
        if(inventory == null) get();
        Map<Integer, Object> items = new HashMap<>();

        for(int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(0);
            if(item != null) items.put(i, item);
        }

        return items;
    }

    @Override
    public Object get() {
        if (inventory == null) update();
        if (!MenuListener.menus.containsKey(inventory)) MenuListener.menus.put(inventory, this);
        return inventory;
    }

    @Override
    public Menu update() {
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