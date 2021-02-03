package com.focamacho.seallibrary.menu;

import com.focamacho.seallibrary.menu.item.IMenuItem;
import com.focamacho.seallibrary.menu.lib.IClick;
import com.focamacho.seallibrary.player.SealPlayer;
import lombok.experimental.Accessors;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

import java.util.*;

@SuppressWarnings("unused")
@Accessors(chain = true)
public class MenuSponge implements IMenu {

    protected final Object plugin;
    protected String title;
    protected int rows;
    protected Inventory inventory;
    protected Map<Integer, IMenuItem> items = new HashMap<>();

    public MenuSponge(Object plugin) {
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
        return inventory;
    }

    @Override
    public IMenu update() {
        if(inventory == null) {
            this.inventory = Inventory.builder()
                    .of(InventoryArchetypes.CHEST)
                    .property(InventoryTitle.of(Text.of(title)))
                    .property(InventoryDimension.of(9, rows))
                    .listener(ClickInventoryEvent.class, event -> {
                        event.setCancelled(true);
                        if (event.getSlot().isPresent()) {
                            Optional<SlotIndex> index = event.getSlot().get().getInventoryProperty(SlotIndex.class);
                            if(!index.isPresent()) return;
                            Integer slotIndex = index.get().getValue();

                            /*onClick.run(event);*/
                            if(event instanceof ClickInventoryEvent.Double) { /*onDouble.run(event);*/ if(items.containsKey(slotIndex)) { items.get(slotIndex).getOnDouble().run(getClick(event)); }}
                            else if(event instanceof ClickInventoryEvent.Shift.Primary) { /*onShift.run(event);*/ if(items.containsKey(slotIndex)) { items.get(slotIndex).getOnShift().run(getClick(event)); }}
                            else if(event instanceof ClickInventoryEvent.Shift.Secondary) { /*onShiftSecondary.run(event);*/ if(items.containsKey(slotIndex)) { items.get(slotIndex).getOnShiftSecondary().run(getClick(event)); }}
                            else if(event instanceof ClickInventoryEvent.Primary) { /*onPrimary.run(event);*/ if(items.containsKey(slotIndex)) { items.get(slotIndex).getOnPrimary().run(getClick(event)); }}
                            else if(event instanceof ClickInventoryEvent.Middle) { /*onMiddle.run(event);*/ if(items.containsKey(slotIndex)) { items.get(slotIndex).getOnMiddle().run(getClick(event)); }}
                            else if(event instanceof ClickInventoryEvent.Secondary) { /*onSecondary.run(event);*/ if(items.containsKey(slotIndex)) { items.get(slotIndex).getOnSecondary().run(getClick(event)); }}
                            else if(event instanceof ClickInventoryEvent.Drop.Full) { /*onDropAll.run(event);*/ if(items.containsKey(slotIndex)) { items.get(slotIndex).getOnDropAll().run(getClick(event)); }}
                            else if(event instanceof ClickInventoryEvent.Drop) { /*onDrop.run(event);*/ if(items.containsKey(slotIndex)) { items.get(slotIndex).getOnDrop().run(getClick(event)); }}
                            else if(event instanceof ClickInventoryEvent.NumberPress) { /*onNumber.run(event);*/ if(items.containsKey(slotIndex)) { items.get(slotIndex).getOnNumber().run(getClick(event)); }}
                        }
                    })
                    /*.listener(InteractInventoryEvent.class, event -> {
                        if (event instanceof InteractInventoryEvent.Open) onOpen.run(event);
                        else if (event instanceof InteractInventoryEvent.Close) onClose.run(event);
                    })*/.build(plugin);
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

    private IClick getClick(ClickInventoryEvent event) {
        return () -> {
            if(event.getSource() instanceof Player) return SealPlayer.get(event.getSource());
            return null;
        };
    }

}