package com.focamacho.seallibrary.server;

import com.focamacho.seallibrary.impl.Implementations;

@SuppressWarnings("unused")
public class SealServer {

    /**
     * Retorna a instância atual do
     * servidor.
     * @return a instância do servidor.
     */
    public static ISealServer get() {
        return Implementations.server;
    }

}
