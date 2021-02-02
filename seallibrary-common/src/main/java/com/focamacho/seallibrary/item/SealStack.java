package com.focamacho.seallibrary.item;

import com.focamacho.seallibrary.impl.Implementations;

public class SealStack {

    /**
     * Obtêm um SealStack a partir do
     * item original.
     * @param item o item original.
     * @return o SealStack obtido.
     */
    public static ISealStack get(Object item) {
        return Implementations.stackBuilder.get(item);
    }

    /**
     * Cria um SealStack a partir de
     * um id.
     * O id deve seguir o padrão
     * modid:itemid.
     * Exemplo: minecraft:grass.
     * @param item o id do item.
     * @return o SealStack criado.
     */
    public static ISealStack get(String item) {
        return Implementations.stackBuilder.get(item);
    }

}
