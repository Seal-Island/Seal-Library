package com.focamacho.seallibrary.menu.item;

import com.focamacho.seallibrary.item.ISealStack;
import com.focamacho.seallibrary.menu.lib.Runnable;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Classe contendo metódos para
 * a criação de um item
 * simples no menu.
 * Esse item funciona como se estivesse em
 * um inventário normal, e não possui nenhum click
 * cancelado (por padrão).
 * @see IMenuItem
 */
@SuppressWarnings("unused")
@Accessors(chain = true)
public class SimpleItem implements IMenuItem {

    @Setter private int slot;
    @Setter private ISealStack item;
    @Setter private Runnable.ClickRunnable onPrimary = (event) -> event.setCancelled(false);
    @Setter private Runnable.ClickRunnable onMiddle = (event) -> event.setCancelled(false);
    @Setter private Runnable.ClickRunnable onSecondary = (event) -> event.setCancelled(false);
    @Setter private Runnable.ClickRunnable onShift = (event) -> event.setCancelled(false);
    @Setter private Runnable.ClickRunnable onDouble = (event) -> event.setCancelled(false);
    @Setter private Runnable.ClickRunnable onDrop = (event) -> event.setCancelled(false);
    @Setter private Runnable.ClickRunnable onShiftSecondary = (event) -> event.setCancelled(false);
    @Setter private Runnable.ClickRunnable onDropAll = (event) -> event.setCancelled(false);
    @Setter private Runnable.ClickRunnable onNumber = (event) -> event.setCancelled(false);

    /**
     * Construtor usado privadamente para
     * a criação de um SimpleItem por meio
     * do método create().
     * @param slot o slot do item no inventário.
     * @param item o item.
     */
    private SimpleItem(int slot, ISealStack item) {
        this.slot = slot;
        this.item = item;
    }

    /**
     * Cria um SimpleItem.
     * @param slot o slot do item no inventário.
     * @param item o item.
     * @return o SimpleItem criado.
     */
    public static SimpleItem create(int slot, ISealStack item) {
        return new SimpleItem(slot, item);
    }

    @Override
    public int getSlot() {
        return this.slot;
    }

    @Override
    public ISealStack getItem() {
        return this.item;
    }

    @Override
    public Runnable.ClickRunnable getOnPrimary() {
        return this.onPrimary;
    }

    @Override
    public Runnable.ClickRunnable getOnMiddle() {
        return this.onMiddle;
    }

    @Override
    public Runnable.ClickRunnable getOnSecondary() {
        return this.onSecondary;
    }

    @Override
    public Runnable.ClickRunnable getOnShift() {
        return this.onShift;
    }

    @Override
    public Runnable.ClickRunnable getOnDouble() {
        return this.onDouble;
    }

    @Override
    public Runnable.ClickRunnable getOnDrop() {
        return this.onDrop;
    }

    @Override
    public Runnable.ClickRunnable getOnShiftSecondary() {
        return this.onShiftSecondary;
    }

    @Override
    public Runnable.ClickRunnable getOnDropAll() {
        return this.onDropAll;
    }

    @Override
    public Runnable.ClickRunnable getOnNumber() {
        return this.onNumber;
    }

    @Override
    public IMenuItem copy() {
        return create(this.slot, this.item.copy())
                .setOnPrimary(this.onPrimary)
                .setOnMiddle(this.onMiddle)
                .setOnSecondary(this.onSecondary)
                .setOnShift(this.onShift)
                .setOnDouble(this.onDouble)
                .setOnDrop(this.onDrop)
                .setOnNumber(this.onNumber)
                .setOnShiftSecondary(this.onShiftSecondary)
                .setOnDropAll(this.onDropAll);
    }
}
