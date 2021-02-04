package com.focamacho.seallibrary.forge;

import com.focamacho.seallibrary.impl.Implementations;
import com.focamacho.seallibrary.item.ISealStack;

/**
 * Classe static para acesso dos métodos
 * de utilidades para compatibilidae com Forge.
 *
 * @see IForgeUtils para mais informações
 * sobre os métodos.
 */
@SuppressWarnings("unused")
public class ForgeUtils {

    public static Object getForgeStack(Object item) {
        return Implementations.forgeUtils.getForgeStack(item);
    }

    public static ISealStack getServerStack(Object item) {
        return Implementations.forgeUtils.getServerStack(item);
    }

}
