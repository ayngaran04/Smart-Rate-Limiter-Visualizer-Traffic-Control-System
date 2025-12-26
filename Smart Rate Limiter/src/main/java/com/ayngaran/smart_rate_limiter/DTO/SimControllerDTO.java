package com.ayngaran.smart_rate_limiter.DTO;

public class SimControllerDTO {
    private String clientId;
    private String algorithm;
    private int capacity;
    private int rate;

    public SimControllerDTO() {}

    public String getClientId() {
        return clientId;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getRate() {
        return rate;
    }
}

