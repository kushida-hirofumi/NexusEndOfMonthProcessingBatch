package com.nexus.NexusEndOfMonthProcessingBatch.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomLogger {
    protected final static Logger logger = LoggerFactory.getLogger(CustomLogger.class);

    public void print(String value) {
        logger.info(value);
    }

    public void error(String value) {
        logger.error(value);
    }
}