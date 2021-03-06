package com.focamacho.seallibrary.logger;

import java.util.logging.Logger;

public class LoggerBukkit implements ILogger {

    private final Logger logger;

    public LoggerBukkit(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void warning(String message) {
        logger.warning(message);
    }

    @Override
    public void error(String message) {
        logger.severe(message);
    }
}
