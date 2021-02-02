package com.focamacho.seallibrary.menu.item;

import com.focamacho.seallibrary.item.ISealStack;
import com.focamacho.seallibrary.menu.lib.Runnable;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Classe contendo metódos para
 * a criação de um item
 * clicável para um menu.
 * @see IMenuItem
 */
@SuppressWarnings("unused")
@Accessors(chain = true)
public class ClickableItem implements IMenuItem {

    @Setter private int slot;
    @Setter private ISealStack item;
    @Setter private Runnable.ClickRunnable onPrimary = (event) -> {};
    @Setter private Runnable.ClickRunnable onMiddle = (event) -> {};
    @Setter private Runnable.ClickRunnable onSecondary = (event) -> {};
    @Setter private Runnable.ClickRunnable onShift = (event) -> {};
    @Setter private Runnable.ClickRunnable onDouble = (event) -> {};
    @Setter private Runnable.ClickRunnable onDrop = (event) -> {};
    @Setter private Runnable.ClickRunnable onShiftSecondary = (event) -> {};
    @Setter private Runnable.ClickRunnable onDropAll = (event) -> {};
    @Setter private Runnable.ClickRunnable onNumber = (event) -> {};

    /**
     * Construtor usado privadamente para
     * a criação de um ClickableItem por meio
     * do método create().
     * @param slot o slot do item no inventário.
     * @param item o item.
     */
    private ClickableItem(int slot, ISealStack item) {
        this.slot = slot;
        this.item = item;
    }

    /**
     * Cria um ClickableItem.
     * @param slot o slot do item no inventário.
     * @param item o item.
     * @return o ClickableItem criado.
     */
    public static ClickableItem create(int slot, ISealStack item) {
        return new ClickableItem(slot, item);
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
