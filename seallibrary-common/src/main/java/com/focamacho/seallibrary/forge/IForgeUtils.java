package com.focamacho.seallibrary.forge;

import com.focamacho.seallibrary.item.ISealStack;

public interface IForgeUtils {

    /**
     * Converte o ItemStack inserido
     * para a sua versão no Forge.
     * @param item o ItemStack do servidor.
     * @return o ItemStack do forge. Retorna um
     * ItemStack vazio caso não tenha sido possível
     * converter o item.
     */
    Object getForgeStack(Object item);

    /**
     * Converte o ItemStack inserido
     * para a sua versão no servidor.
     * @param item o ItemStack do forge.
     * @return o SealStack criado. Retorna um
     * ItemStack vazio caso não tenha sido possível
     * converter o item.
     */
    ISealStack getServerStack(Object item);

}
