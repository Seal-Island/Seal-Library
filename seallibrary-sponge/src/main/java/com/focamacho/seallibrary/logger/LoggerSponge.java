package com.focamacho.seallibrary.logger;

import org.slf4j.Logger;

public class LoggerSponge implements ILogger {

    private final Logger logger;

    public LoggerSponge(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(String message) {
        this.logger.info(message);
    }

    @Override
    public void warning(String message) {
        this.logger.warn(message);
    }

    @Override
    public void error(String message) {
        this.logger.error(message);
    }

}
