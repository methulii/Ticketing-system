package com.example.ticketing.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class
LogUtil {
    // Create a logger for this class
    private static final Logger logger = LoggerFactory.getLogger(LogUtil.class);

    // Method to log info level messages
    public static void logInfo(String message) {
        logger.info(message);
    }

    // Method to log warning level messages
    public static void logWarn(String message) {
        logger.warn(message);
    }

    // Method to log error level messages
    public static void logError(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);  // Return a logger specific to the class
    }
}

