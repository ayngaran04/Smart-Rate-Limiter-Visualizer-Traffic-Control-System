package com.ayngaran.smart_rate_limiter.Service;
import com.ayngaran.smart_rate_limiter.Service.Algorithms.FixedWindowAlgorithm;
import com.ayngaran.smart_rate_limiter.Service.Algorithms.LeakyBucketAlgorithm;
import com.ayngaran.smart_rate_limiter.Service.Algorithms.TokenBucketAlgorithm;
import org.springframework.stereotype.Service;



@Service
public class RateLimiterService {
    private final TokenBucketAlgorithm tokenBucketAlgorithm;
    private final FixedWindowAlgorithm fixedWindowAlgorithm;
    private final LeakyBucketAlgorithm leakyBucketAlgorithm;

    public RateLimiterService(TokenBucketAlgorithm tokenBucketAlgorithm, FixedWindowAlgorithm fixedWindowAlgorithm, LeakyBucketAlgorithm leakyBucketAlgorithm) {
        this.tokenBucketAlgorithm = tokenBucketAlgorithm;
        this.fixedWindowAlgorithm = fixedWindowAlgorithm;
        this.leakyBucketAlgorithm = leakyBucketAlgorithm;
    }
    public boolean runToken(String userId){
        return tokenBucketAlgorithm.allowRequest(userId);
    }
    public boolean runFixedWindow(String userId){
        return fixedWindowAlgorithm.allowRequest(userId);
    }
    public boolean runLeakyBucket(String userId){
        return leakyBucketAlgorithm.allowRequest(userId);
    }
}
