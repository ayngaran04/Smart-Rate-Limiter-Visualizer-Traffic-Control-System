package com.ayngaran.smart_rate_limiter.Service.Algorithms;

import org.springframework.stereotype.Component;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class LeakyBucketAlgorithm implements RateLimiter {

    public static class Bucket {
        Queue<Long> queue = new ConcurrentLinkedQueue<>();
        long lastLeakTime = System.currentTimeMillis();
        int capacity;
        int leakRate;
    }

    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    private void process(Bucket bucket) {
        long now = System.currentTimeMillis();
        if (now - bucket.lastLeakTime >= 1000) {
            int counter = 0;
            while (!bucket.queue.isEmpty() && counter < bucket.leakRate) {
                bucket.queue.poll();
                counter++;
            }
            bucket.lastLeakTime = now;
        }
    }

    @Override
    public boolean allowRequest(String id, int capacity, int reqPerSecond) {
        Bucket bucket = buckets.computeIfAbsent(id, k -> {
            Bucket b = new Bucket();
            b.capacity = capacity;
            b.leakRate = reqPerSecond;
            return b;
        });


        bucket.capacity = capacity;
        bucket.leakRate = reqPerSecond;

        process(bucket);

        if (bucket.queue.size() >= bucket.capacity) {
            return false;
        }
        bucket.queue.add(System.currentTimeMillis());
        return true;
    }
}