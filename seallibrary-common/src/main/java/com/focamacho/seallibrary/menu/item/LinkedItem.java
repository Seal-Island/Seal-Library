package com.focamacho.seallibrary.menu.item;

import com.focamacho.seallibrary.item.ISealStack;
import com.focamacho.seallibrary.menu.lib.Runnable;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe contendo metódos para
 * a criação de um item linkado a outro
 * para um menu.
 *
 * Um LinkedItem serve para que seja possível a
 * criação de um item com ações idênticas a de outro,
 * porém com um ItemStack diferente.
 *
 * Isso permite que você faça alterações somente no
 * item original, e o item linkado receberá as mesmas
 * alterações.
 */
public class LinkedItem implements IMenuItem {

    @Setter private int slot;
    @Setter private ISealStack item;
    @Getter @Setter private IMenuItem linkedItem;

    /**
     * Construtor usado privadamente para
     * a criação de um LinkedItem por meio
     * do método create().
     * @param slot o slot do item no inventário.
     * @param item o item.
     */
    private LinkedItem(int slot, ISealStack item, IMenuItem linkedItem) {
        this.slot = slot;
        this.item = item;
        this.linkedItem = linkedItem;
    }

    /**
     * Cria um LinkedItem.
     * @param slot o slot do item no inventário.
     * @param item o item.
     * @param linkedItem o item para ser linkado.
     * @return o LinkedItem criado.
     */
    public static LinkedItem create(int slot, ISealStack item, IMenuItem linkedItem) {
        return new LinkedItem(slot, item, linkedItem);
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
        return this.linkedItem.getOnPrimary();
    }

    @Override
    public Runnable.ClickRunnable getOnMiddle() {
        return this.linkedItem.getOnMiddle();
    }

    @Override
    public Runnable.ClickRunnable getOnSecondary() {
        return this.linkedItem.getOnSecondary();
    }

    @Override
    public Runnable.ClickRunnable getOnShift() {
        return this.linkedItem.getOnShift();
    }

    @Override
    public Runnable.ClickRunnable getOnDouble() {
        return this.linkedItem.getOnDouble();
    }

    @Override
    public Runnable.ClickRunnable getOnDrop() {
        return this.linkedItem.getOnDrop();
    }

    @Override
    public Runnable.ClickRunnable getOnShiftSecondary() {
        return this.linkedItem.getOnShiftSecondary();
    }

    @Override
    public Runnable.ClickRunnable getOnDropAll() {
        return this.linkedItem.getOnDropAll();
    }

    @Override
    public Runnable.ClickRunnable getOnNumber() {
        return this.linkedItem.getOnNumber();
    }

    @Override
    public IMenuItem copy() {
        return create(this.slot, this.item.copy(), this.linkedItem);
    }

}
