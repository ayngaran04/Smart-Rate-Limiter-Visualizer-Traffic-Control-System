package com.ayngaran.smart_rate_limiter.Service.Algorithms;

import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenBucketAlgorithm implements RateLimiter {

    // Store config INSIDE the bucket so every user can have different limits
    public static class Bucket {
        double tokens;
        long lastRefillTime;
        int capacity;
        int refillRate;
    }

    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Override
    public boolean allowRequest(String id, int capacity, int refillRate) {
        Bucket bucket = buckets.computeIfAbsent(id, k -> {
            Bucket x = new Bucket();
            x.capacity = capacity;
            x.refillRate = refillRate;
            x.tokens = capacity;
            x.lastRefillTime = System.currentTimeMillis();
            return x;
        });

        // Optional: Update bucket config if the request parameters changed
        bucket.capacity = capacity;
        bucket.refillRate = refillRate;

        synchronized (bucket) {
            refill(bucket);
            if (bucket.tokens >= 1) {
                bucket.tokens--;
                return true;
            }
            return false;
        }
    }

    private void refill(Bucket bucket) {
        long now = System.currentTimeMillis();
        double secondsGone = (now - bucket.lastRefillTime) / 1000.0;
        double tokensToAdd = secondsGone * bucket.refillRate;

        if (tokensToAdd > 0) {
            bucket.tokens = Math.min(bucket.capacity, bucket.tokens + tokensToAdd);
            bucket.lastRefillTime = now;
        }
    }
}