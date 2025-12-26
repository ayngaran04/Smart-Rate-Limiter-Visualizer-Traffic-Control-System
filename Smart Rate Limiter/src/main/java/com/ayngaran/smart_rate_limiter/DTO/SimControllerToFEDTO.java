package com.ayngaran.smart_rate_limiter.DTO;
public class SimControllerToFEDTO {

    private String clientId;
    private String algorithm;
    private boolean allowed;

    public SimControllerToFEDTO(
            String clientId,
            String algorithm,
            boolean allowed
    ) {
        this.clientId = clientId;
        this.algorithm = algorithm;
        this.allowed = allowed;
    }

    public String getClientId() {
        return clientId;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public boolean isAllowed() {
        return allowed;
    }
}

