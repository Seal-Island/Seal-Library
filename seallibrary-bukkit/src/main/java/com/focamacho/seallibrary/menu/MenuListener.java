package com.focamacho.seallibrary.menu;

import com.focamacho.seallibrary.item.ISealStack;
import com.focamacho.seallibrary.item.SealStack;
import com.focamacho.seallibrary.menu.lib.AbstractClick;
import com.focamacho.seallibrary.menu.lib.AbstractInteract;
import com.focamacho.seallibrary.player.ISealPlayer;
import com.focamacho.seallibrary.player.SealPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MenuListener implements Listener {
    
    public static Map<Inventory, MenuBukkit> menus = new HashMap<>();

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        MenuBukkit menu = menus.get(event.getInventory());
        if(menu != null) {
            event.setCancelled(true);
            AbstractClick click = getClick(event);

            int slotIndex = event.getSlot();
            ClickType type = event.getClick();

            menu.getOnClick().run(click);
            if(!click.isBreakNow()) {
                switch (type) {
                    case DOUBLE_CLICK:
                        menu.getOnDouble().run(click);
                        Optional.ofNullable(menu.getItem(slotIndex)).ifPresent(item -> item.getOnDouble().run(click));
                        break;
                    case SHIFT_LEFT:
                        menu.getOnShift().run(click);
                        Optional.ofNullable(menu.getItem(slotIndex)).ifPresent(item -> item.getOnShift().run(click));
                        break;
                    case SHIFT_RIGHT:
                        menu.getOnShiftSecondary().run(click);
                        Optional.ofNullable(menu.getItem(slotIndex)).ifPresent(item -> item.getOnShiftSecondary().run(click));
                        break;
                    case LEFT:
                        menu.getOnPrimary().run(click);
                        Optional.ofNullable(menu.getItem(slotIndex)).ifPresent(item -> item.getOnPrimary().run(click));
                        break;
                    case MIDDLE:
                        menu.getOnMiddle().run(click);
                        Optional.ofNullable(menu.getItem(slotIndex)).ifPresent(item -> item.getOnMiddle().run(click));
                        break;
                    case RIGHT:
                        menu.getOnSecondary().run(click);
                        Optional.ofNullable(menu.getItem(slotIndex)).ifPresent(item -> item.getOnSecondary().run(click));
                        break;
                    case CONTROL_DROP:
                        menu.getOnDropAll().run(click);
                        Optional.ofNullable(menu.getItem(slotIndex)).ifPresent(item -> item.getOnDropAll().run(click));
                        break;
                    case DROP:
                        menu.getOnDrop().run(click);
                        Optional.ofNullable(menu.getItem(slotIndex)).ifPresent(item -> item.getOnDrop().run(click));
                        break;
                    case NUMBER_KEY:
                        menu.getOnNumber().run(click);
                        Optional.ofNullable(menu.getItem(slotIndex)).ifPresent(item -> item.getOnNumber().run(click));
                }
            }
            event.setCancelled(click.isCancelled());
        }
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent event) {
        Inventory inventory = event.getInventory();
        if(menus.containsKey(inventory)) {
            AbstractInteract interact = new AbstractInteract() {
                @Override
                public ISealPlayer getPlayer() {
                    return SealPlayer.get(event.getPlayer());
                }

                @Override
                public Object getInventory() {
                    return inventory;
                }
            };
            menus.get(inventory).getOnOpen().run(interact);
            event.setCancelled(interact.isCancelled());
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        if(menus.containsKey(inventory)) {
            MenuBukkit menu = menus.get(inventory);

            AbstractInteract interact = new AbstractInteract() {
                @Override
                public ISealPlayer getPlayer() {
                    return SealPlayer.get(event.getPlayer());
                }

                @Override
                public Object getInventory() {
                    return inventory;
                }
            };
            menu.getOnClose().run(interact);
            if(interact.isCancelled()) {
                event.getPlayer().openInventory(inventory);
            } else if(inventory.getViewers().size() <= 0) {
                menus.remove(inventory);
            }
        }
    }
    
    private AbstractClick getClick(InventoryClickEvent event) {
        return new AbstractClick() {
            @Override
            public ISealPlayer getPlayer() {
                return SealPlayer.get(event.getWhoClicked());
            }

            @Override
            public Object getInventory() {
                return event.getInventory();
            }

            @Override
            public ISealStack getItem() {
                return SealStack.get(event.getCurrentItem());
            }

            @Override
            public ISealStack getCursor() {
                return SealStack.get(event.getCursor());
            }

            @Override
            public int getSlot() {
                return event.getRawSlot();
            }

            @Override
            public com.focamacho.seallibrary.menu.lib.ClickType getType() {
                switch (event.getClick()) {
                    case DOUBLE_CLICK:
                        return com.focamacho.seallibrary.menu.lib.ClickType.DOUBLE;
                    case SHIFT_LEFT:
                        return com.focamacho.seallibrary.menu.lib.ClickType.SHIFT;
                    case SHIFT_RIGHT:
                        return com.focamacho.seallibrary.menu.lib.ClickType.SHIFT_SECONDARY;
                    case LEFT:
                        return com.focamacho.seallibrary.menu.lib.ClickType.PRIMARY;
                    case MIDDLE:
                        return com.focamacho.seallibrary.menu.lib.ClickType.MIDDLE;
                    case RIGHT:
                        return com.focamacho.seallibrary.menu.lib.ClickType.SECONDARY;
                    case CONTROL_DROP:
                        return com.focamacho.seallibrary.menu.lib.ClickType.DROP_ALL;
                    case DROP:
                        return com.focamacho.seallibrary.menu.lib.ClickType.DROP;
                    case NUMBER_KEY:
                        return com.focamacho.seallibrary.menu.lib.ClickType.NUMBER;
                }
                return com.focamacho.seallibrary.menu.lib.ClickType.PRIMARY;
            }

            @Override
            public int getNumber() {
                if(event.getClick() != ClickType.NUMBER_KEY) return 0;
                return event.getHotbarButton();
            }
        };
    }

}
