package com.ayngaran.smart_rate_limiter.Service.Algorithms;

import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FixedWindowAlgorithm implements RateLimiter {

    public static class Bucket {
        long lastFilledTime;
        int requests;
        int limit;
    }

    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Override
    public boolean allowRequest(String id, int capacity, int unusedRate) {

        Bucket b = buckets.computeIfAbsent(id, k -> {
            Bucket x = new Bucket();
            x.limit = capacity;
            x.requests = capacity;
            x.lastFilledTime = System.currentTimeMillis();
            return x;
        });

        b.limit = capacity;

        synchronized (b) {
            refill(b);
            if (b.requests >= 1) {
                b.requests--;
                return true;
            }
            return false;
        }
    }

    public void refill(Bucket b) {
        long now = System.currentTimeMillis();

        if ((now - b.lastFilledTime) >= 60000) {
            b.requests = b.limit;
            b.lastFilledTime = now;
        }
    }
}