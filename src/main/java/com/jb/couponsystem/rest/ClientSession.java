package com.jb.couponsystem.rest;

public class ClientSession {
    private long clientId;
    private String type;
    private long lastAccessedMillis;

    private ClientSession(long clientId, String type, long currentTimeMillis) {
        this.clientId = clientId;
        this.type = type;
        this.lastAccessedMillis = currentTimeMillis;
    }

    public static ClientSession create(long clientId, String type) {
        return new ClientSession(clientId, type, System.currentTimeMillis());
    }

    public void access() {
        lastAccessedMillis = System.currentTimeMillis();
    }

    public long getClientId() {
        return clientId;
    }

    public String getType() {
        return type;
    }

    public long getLastAccessedMillis() {
        return lastAccessedMillis;
    }
}
