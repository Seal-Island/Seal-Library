package com.focamacho.seallibrary.logger;

import com.focamacho.seallibrary.impl.Implementations;

/**
 * Classe static para acesso
 * aos métodos de Logging.
 * @see ILogger
 */
public class SealLogger {

    /**
     * Exibe uma informação nos logs.
     * @param message a mensagem desejada.
     */
    public static void info(String message) {
        Implementations.logger.info(message);
    }

    /**
     * Exibe um aviso nos logs.
     * @param message a mensagem desejada.
     */
    public static void warning(String message) {
        Implementations.logger.warning(message);
    }

    /**
     * Exibe um erro nos logs.
     * @param message a mensagem desejada.
     */
    public static void error(String message) {
        Implementations.logger.error(message);
    }

}
