package com.focamacho.seallibrary.menu;

import com.focamacho.seallibrary.menu.lib.IClick;
import com.focamacho.seallibrary.player.SealPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class MenuListener implements Listener {
    
    public static Map<Inventory, MenuBukkit> menus = new HashMap<>();

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        MenuBukkit menu = menus.get(event.getInventory());
        if(menu != null) {
            event.setCancelled(true);

            int slotIndex = event.getSlot();
            ClickType type = event.getClick();

            /*menu.onClick.run(event);*/
            if(type == ClickType.DOUBLE_CLICK) { /*menu.onDouble.run(event);*/ if(menu.items.containsKey(slotIndex)) { menu.items.get(slotIndex).getOnDouble().run(getClick(event)); }}
            else if(type == ClickType.SHIFT_LEFT) { /*menu.onShift.run(event);*/ if(menu.items.containsKey(slotIndex)) { menu.items.get(slotIndex).getOnShift().run(getClick(event)); }}
            else if(type == ClickType.SHIFT_RIGHT) { /*menu.onShiftSecondary.run(event);*/ if(menu.items.containsKey(slotIndex)) { menu.items.get(slotIndex).getOnShiftSecondary().run(getClick(event)); }}
            else if(type == ClickType.LEFT) { /*menu.onPrimary.run(event);*/ if(menu.items.containsKey(slotIndex)) { menu.items.get(slotIndex).getOnPrimary().run(getClick(event)); }}
            else if(type == ClickType.MIDDLE) { /*menu.onMiddle.run(event);*/ if(menu.items.containsKey(slotIndex)) { menu.items.get(slotIndex).getOnMiddle().run(getClick(event)); }}
            else if(type == ClickType.RIGHT) { /*menu.onSecondary.run(event);*/ if(menu.items.containsKey(slotIndex)) { menu.items.get(slotIndex).getOnSecondary().run(getClick(event)); }}
            else if(type == ClickType.CONTROL_DROP) { /*menu.onDropAll.run(event);*/ if(menu.items.containsKey(slotIndex)) { menu.items.get(slotIndex).getOnDropAll().run(getClick(event)); }}
            else if(type == ClickType.DROP) { /*menu.onDrop.run(event);*/ if(menu.items.containsKey(slotIndex)) { menu.items.get(slotIndex).getOnDrop().run(getClick(event)); }}
            else if(type == ClickType.NUMBER_KEY) { /*menu.onNumber.run(event);*/ if(menu.items.containsKey(slotIndex)) { menu.items.get(slotIndex).getOnNumber().run(getClick(event)); }}
        }
    }
    
    private IClick getClick(InventoryClickEvent event) {
        return () -> {
            if(event.getWhoClicked() instanceof Player) return SealPlayer.get(event.getWhoClicked());
            return null;
        };
    }

}
