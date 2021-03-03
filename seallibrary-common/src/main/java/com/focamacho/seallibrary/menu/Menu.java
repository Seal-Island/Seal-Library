package com.focamacho.seallibrary.menu;

import com.focamacho.seallibrary.impl.Implementations;

@SuppressWarnings("unused")
public class Menu {

    /**
     * Cria um novo menu.
     * que está criando o menu.
     * @return o menu criado.
     */
    public static AbstractMenu create() {
        return Implementations.menuBuilder.create();
    }

    /**
     * Copia um menu.
     * @param menu o menu para ser copiado.
     * @return o menu copiado.
     */
    public static AbstractMenu create(AbstractMenu menu) {
        return menu.copy();
    }

}
