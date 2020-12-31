package com.jb.couponsystem.rest.model;

public class SystemErrorResponse {
    private final String message;
    private final long timestamp;

    private SystemErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public static SystemErrorResponse ofNow(String message) {
        return new SystemErrorResponse(message, System.currentTimeMillis());
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
