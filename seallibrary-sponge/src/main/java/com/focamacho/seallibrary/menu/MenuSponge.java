package com.focamacho.seallibrary.menu;

import com.focamacho.seallibrary.menu.item.IMenuItem;
import com.focamacho.seallibrary.menu.lib.AbstractClick;
import com.focamacho.seallibrary.menu.lib.AbstractInteract;
import com.focamacho.seallibrary.player.ISealPlayer;
import com.focamacho.seallibrary.player.SealPlayer;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("unused")
public class MenuSponge extends AbstractMenu {

    protected Inventory inventory;

    public MenuSponge(Object plugin) {
        this.plugin = plugin;
    }

    @Override
    public Map<Integer, Object> getOriginalItems() {
        Map<Integer, Object> items = new HashMap<>();

        inventory.slots().forEach(slot -> {
            if(slot.peek().isPresent() && !slot.peek().get().isEmpty()) {
                int slotIndex = slot.getInventoryProperty(SlotIndex.class).get().getValue();
                items.put(slotIndex, slot.peek().get());
            }
        });

        return items;
    }

    @Override
    public Object get() {
        if (inventory == null) update();
        return inventory;
    }

    @Override
    public AbstractMenu update() {
        if(inventory == null) {
            this.inventory = Inventory.builder()
                    .of(InventoryArchetypes.CHEST)
                    .property(InventoryTitle.of(Text.of(title)))
                    .property(InventoryDimension.of(9, rows))
                    .listener(ClickInventoryEvent.class, event -> {
                        AbstractClick click = getClick(event);
                        if(event.getSlot().isPresent()) {
                            Optional<SlotIndex> index = event.getSlot().get().getInventoryProperty(SlotIndex.class);
                            if(!index.isPresent()) return;
                            Integer slotIndex = index.get().getValue();

                            onClick.run(click);
                            if(event instanceof ClickInventoryEvent.Double) {
                                onDouble.run(click);
                                if(items.containsKey(slotIndex)) items.get(slotIndex).getOnDouble().run(click);
                            }
                            else if(event instanceof ClickInventoryEvent.Shift.Primary) {
                                onShift.run(click);
                                if(items.containsKey(slotIndex)) items.get(slotIndex).getOnShift().run(click);
                            }
                            else if(event instanceof ClickInventoryEvent.Shift.Secondary) {
                                onShiftSecondary.run(click);
                                if(items.containsKey(slotIndex)) items.get(slotIndex).getOnShiftSecondary().run(click);
                            }
                            else if(event instanceof ClickInventoryEvent.Primary) {
                                onPrimary.run(click);
                                if(items.containsKey(slotIndex)) items.get(slotIndex).getOnPrimary().run(click);
                            }
                            else if(event instanceof ClickInventoryEvent.Middle) {
                                onMiddle.run(click);
                                if(items.containsKey(slotIndex)) items.get(slotIndex).getOnMiddle().run(click);
                            }
                            else if(event instanceof ClickInventoryEvent.Secondary) {
                                onSecondary.run(click);
                                if(items.containsKey(slotIndex)) items.get(slotIndex).getOnSecondary().run(click);
                            }
                            else if(event instanceof ClickInventoryEvent.Drop.Full) {
                                onDropAll.run(click);
                                if(items.containsKey(slotIndex)) items.get(slotIndex).getOnDropAll().run(click);
                            }
                            else if(event instanceof ClickInventoryEvent.Drop) {
                                onDrop.run(click);
                                if(items.containsKey(slotIndex)) items.get(slotIndex).getOnDrop().run(click);
                            }
                            else if(event instanceof ClickInventoryEvent.NumberPress) {
                                onNumber.run(click);
                                if(items.containsKey(slotIndex)) items.get(slotIndex).getOnNumber().run(click);
                            }
                        }
                        event.setCancelled(click.isCancelled());
                    })
                    .listener(InteractInventoryEvent.class, event -> {
                        AbstractInteract interact = getInteract(event);
                        if (event instanceof InteractInventoryEvent.Open) onOpen.run(interact);
                        else if (event instanceof InteractInventoryEvent.Close) onClose.run(interact);
                        event.setCancelled(interact.isCancelled());
                    }).build(plugin);
        }

        int index = 0;
        for (Inventory slot : inventory.slots()) {
            if (items.containsKey(index)) {
                int finalIndex = index;
                Task.builder().execute(() -> {
                    IMenuItem item = items.get(finalIndex);
                    if (!slot.peek().isPresent() || !slot.peek().get().equals(item.getItem().toOriginal()))
                        slot.set((ItemStack) item.getItem().toOriginal());
                }).submit(plugin);
            } else {
                Task.builder().execute(slot::clear).submit(plugin);
            }
            index++;
        }

        return this;
    }

    private AbstractClick getClick(ClickInventoryEvent event) {
        return new AbstractClick() {
            @Override
            public ISealPlayer getPlayer() {
                return SealPlayer.get(event.getSource());
            }

            @Override
            public Object getInventory() {
                return event.getTargetInventory().parent();
            }
        };
    }

    private AbstractInteract getInteract(InteractInventoryEvent event) {
        return new AbstractInteract() {
            @Override
            public ISealPlayer getPlayer() {
                return SealPlayer.get(event.getSource());
            }

            @Override
            public Object getInventory() {
                return event.getTargetInventory().parent();
            }
        };
    }

}