package com.nexus.NexusEndOfMonthProcessingBatch.exception;

/**
 * FreeeApiの例外エラー
 */
public class FreeeApiErrorException extends Exception {
    public FreeeApiErrorException(String message) {
        super(message);
    }
}