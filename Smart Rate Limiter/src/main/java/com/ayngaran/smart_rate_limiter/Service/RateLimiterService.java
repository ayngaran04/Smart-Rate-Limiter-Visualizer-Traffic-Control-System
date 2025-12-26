package com.ayngaran.smart_rate_limiter.Service;

import com.ayngaran.smart_rate_limiter.DTO.SimControllerDTO;
import com.ayngaran.smart_rate_limiter.Service.Algorithms.FixedWindowAlgorithm;
import com.ayngaran.smart_rate_limiter.Service.Algorithms.LeakyBucketAlgorithm;
import com.ayngaran.smart_rate_limiter.Service.Algorithms.TokenBucketAlgorithm;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {
    private final TokenBucketAlgorithm tokenBucketAlgorithm;
    private final FixedWindowAlgorithm fixedWindowAlgorithm;
    private final LeakyBucketAlgorithm leakyBucketAlgorithm;

    public RateLimiterService(TokenBucketAlgorithm tokenBucketAlgorithm,
                              FixedWindowAlgorithm fixedWindowAlgorithm,
                              LeakyBucketAlgorithm leakyBucketAlgorithm) {
        this.tokenBucketAlgorithm = tokenBucketAlgorithm;
        this.fixedWindowAlgorithm = fixedWindowAlgorithm;
        this.leakyBucketAlgorithm = leakyBucketAlgorithm;
    }

    // This is the NEW method you need to add
    public boolean processRequest(SimControllerDTO request) {
        String algoType = request.getAlgorithm();
        String userId = request.getClientId();
        int capacity = request.getCapacity();
        int rate = request.getRate();
        if (algoType == null) return false;

        switch (algoType.toLowerCase()) {
            case "tokenbucket":
            case "token_bucket":
                return tokenBucketAlgorithm.allowRequest(userId, capacity, rate);

            case "leakybucket":
            case "leaky_bucket":
                return leakyBucketAlgorithm.allowRequest(userId, capacity, rate);

            case "fixedwindow":
            case "fixed_window":
                return fixedWindowAlgorithm.allowRequest(userId, capacity, rate);

            default:
                throw new IllegalArgumentException("Unknown algorithm: " + algoType);
        }
    }
}