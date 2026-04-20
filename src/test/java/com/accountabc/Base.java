package com.accountabc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Base {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    public static double initialTotalAsset = 100000;

    protected void logInfo(String message) {
        log.info(message);
    }

    protected void logError(String message) {
        log.error(message);
    }
}
