package com.focamacho.seallibrary.menu;

import com.focamacho.seallibrary.impl.Implementations;

@SuppressWarnings("unused")
public class Menu {

    /**
     * Cria um novo menu.
     * @param plugin a instância do plugin
     * que está criando o menu.
     * @return o menu criado.
     */
    public static AbstractMenu create(Object plugin) {
        return Implementations.menuBuilder.create(plugin);
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
