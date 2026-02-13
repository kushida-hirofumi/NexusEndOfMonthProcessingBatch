package com.nexus.NexusEndOfMonthProcessingBatch.exception;

/**
 * データベース関連のエラー
 */
public class DataBaseErrorException extends Exception {
    public DataBaseErrorException(String message) {
        super(message);
    }
}