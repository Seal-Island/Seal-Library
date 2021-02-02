package com.focamacho.seallibrary.menu.item;

import com.focamacho.seallibrary.item.ISealStack;
import com.focamacho.seallibrary.menu.lib.Runnable;

public interface IMenuItem {

    /**
     * Retorna o SealStack definido
     * nesse item.
     */
    ISealStack getItem();

    /**
     * Retorna o slot definido
     * nesse item.
     */
    int getSlot();

    /**
     * Retorna a ação efetuada
     * ao clicar com o botão esquerdo
     * do mouse no item.
     */
    Runnable.ClickRunnable getOnPrimary();

    /**
     * Retorna a ação efetuada
     * ao clicar com o botão central
     * do mouse no item.
     */
    Runnable.ClickRunnable getOnMiddle();

    /**
     * Retorna a ação efetuada
     * ao clicar com o botão direito
     * do mouse no item.
     */
    Runnable.ClickRunnable getOnSecondary();

    /**
     * Retorna a ação efetuada
     * ao clicar com o shift + botão esquerdo
     * do mouse no item.
     */
    Runnable.ClickRunnable getOnShift();

    /**
     * Retorna a ação efetuada
     * ao clicar duas vezes com
     * o botão esquerdo do
     * mouse no item.
     */
    Runnable.ClickRunnable getOnDouble();

    /**
     * Retorna a ação efetuada
     * ao dropar o item com a tecla
     * Q.
     */
    Runnable.ClickRunnable getOnDrop();

    /**
     * Retorna a ação efetuada
     * ao clicar com o shift + botão direito
     * do mouse no item.
     */
    Runnable.ClickRunnable getOnShiftSecondary();

    /**
     * Retorna a ação efetuada
     * ao dropar o item com a tecla
     * CTRL + Q.
     */
    Runnable.ClickRunnable getOnDropAll();

    /**
     * Retorna a ação efetuada
     * ao clicar com uma tecla
     * numérica no item.
     */
    Runnable.ClickRunnable getOnNumber();

    /**
     * Cria uma cópia desse item.
     */
    IMenuItem copy();

}
