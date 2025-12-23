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
    }

    private final int capacity;
    private final int reqPerSecond;
    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    public LeakyBucketAlgorithm() {
        this.capacity = 10;
        this.reqPerSecond = 5;
    }

    public LeakyBucketAlgorithm(int capacity, int reqPerSecond) {
        this.capacity = capacity;
        this.reqPerSecond = reqPerSecond;
    }

    private void process(String id) {
        Bucket bucket = buckets.get(id);
        long now = System.currentTimeMillis();

        if (now - bucket.lastLeakTime >= 1000) {
            int counter = 0;
            while (!bucket.queue.isEmpty() && counter < reqPerSecond) {
                bucket.queue.poll();
                counter++;
            }
            bucket.lastLeakTime = now;
        }
    }

    @Override
    public boolean allowRequest(String id) {
        buckets.putIfAbsent(id, new Bucket());
        Bucket bucket = buckets.get(id);

        if (bucket.queue.size() >= capacity) {
            return false;
        }
        process(id);
        bucket.queue.add(System.currentTimeMillis());
        return true;
    }
}
