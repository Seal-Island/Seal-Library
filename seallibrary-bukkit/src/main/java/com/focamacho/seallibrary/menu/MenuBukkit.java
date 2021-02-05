package com.focamacho.seallibrary.menu;

import com.focamacho.seallibrary.menu.item.IMenuItem;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class MenuBukkit implements IMenu {

    protected final Object plugin;
    protected String title;
    protected int rows;
    protected Inventory inventory;
    protected Map<Integer, IMenuItem> items = new HashMap<>();

    public MenuBukkit(Object plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public IMenu setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public int getRows() {
        return this.rows;
    }

    @Override
    public IMenu setRows(int rows) {
        this.rows = rows;
        return this;
    }

    @Override
    public IMenuItem getItem(int slot) {
        return items.get(slot);
    }

    @Override
    public List<IMenuItem> getItems() {
        return new ArrayList<>(this.items.values());
    }

    @Override
    public IMenu clearItems() {
        items.clear();
        return this;
    }

    @Override
    public Object getPlugin() {
        return this.plugin;
    }

    @Override
    public Object get() {
        if (inventory == null) update();
        if (!MenuListener.menus.containsKey(inventory)) MenuListener.menus.put(inventory, this);
        return inventory;
    }

    @Override
    public IMenu update() {
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

    @Override
    public IMenu setItems(List<IMenuItem> items) {
        this.items.clear();
        items.forEach(this::addItem);
        return this;
    }

    @Override
    public IMenu addItem(IMenuItem item) {
        items.put(item.getSlot(), item);
        return this;
    }

}