package com.focamacho.seallibrary.logger;

/**
 * Essa interface se torna necessária pois o Sponge e o Bukkit
 * usam frameworks diferentes para Logging.
 */
public interface ILogger {

    /**
     * Exibe uma informação nos logs.
     * @param message a mensagem desejada.
     */
    void info(String message);

    /**
     * Exibe um aviso nos logs.
     * @param message a mensagem desejada.
     */
    void warning(String message);

    /**
     * Exibe um erro nos logs.
     * @param message a mensagem desejada.
     */
    void error(String message);

}
