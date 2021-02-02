package com.focamacho.seallibrary.impl;

import com.focamacho.seallibrary.item.ISealStack;
import com.focamacho.seallibrary.menu.IMenu;
import com.focamacho.seallibrary.player.ISealPlayer;

public class ImpInterfaces {

    /**
     * Interface usada para a obtenção
     * de um Menu.
     */
    public interface MenuBuilder {
        IMenu create(Object plugin);
    }

    /**
     * Interface usada para a obtenção
     * de um SealPlayer
     */
    public interface ISealPlayerGetter {
        ISealPlayer get(Object player);
    }

    /**
     * Interface usada para a obtenção
     * de um SealStack.
     */
    public interface IStackBuilder {
        ISealStack get(Object item);
        ISealStack get(String item);
    }
}
