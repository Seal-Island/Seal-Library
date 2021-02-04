package com.focamacho.seallibrary.forge;

import com.focamacho.seallibrary.item.ISealStack;

public interface IForgeUtils {

    /**
     * Converte o ItemStack inserido
     * para a sua versão no Forge.
     * @param item o ItemStack do servidor.
     * @return o ItemStack do forge.
     */
    Object getForgeStack(Object item);

    /**
     * Converte o ItemStack inserido
     * para a sua versão no servidor.
     * @param item o ItemStack do forge.
     * @return o SealStack criado.
     */
    ISealStack getServerStack(Object item);

}
