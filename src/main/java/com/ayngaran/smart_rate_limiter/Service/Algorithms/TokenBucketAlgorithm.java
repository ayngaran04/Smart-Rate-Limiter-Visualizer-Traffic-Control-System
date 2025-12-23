package com.ayngaran.smart_rate_limiter.Service.Algorithms;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
@Component
public class TokenBucketAlgorithm implements RateLimiter {
    public static class Bucket{
        double tokens;
        long lastRefillTime;
    }
    private final int capacity;
    private final int  refillRate;
    private final ConcurrentHashMap<String , Bucket> buckets=new ConcurrentHashMap<>();

    public TokenBucketAlgorithm() {
        this.capacity = 10;
        this.refillRate = 5;
    }

    public TokenBucketAlgorithm(int capacity, int refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
    }
    @Override
    public boolean allowRequest(String id) {
        Bucket bucket = buckets.computeIfAbsent(id,k->{
            Bucket x = new Bucket();
            x.tokens = capacity;
            x.lastRefillTime = System.currentTimeMillis();
            return x;
        });
        synchronized (bucket){
            refill(bucket);
            if(bucket.tokens>=1){
                bucket.tokens--;
                return true;
            }
            return false;
        }
    }

    private void refill(Bucket bucket) {
        long now = System.currentTimeMillis();
        double secondGone = (now - bucket.lastRefillTime) / 1000.0;
        double tokenToAdd = secondGone * refillRate ;

        if(tokenToAdd>0){
          bucket.tokens = Math.min(capacity, bucket.tokens + tokenToAdd);
          bucket.lastRefillTime = now;
        }
    }


}
