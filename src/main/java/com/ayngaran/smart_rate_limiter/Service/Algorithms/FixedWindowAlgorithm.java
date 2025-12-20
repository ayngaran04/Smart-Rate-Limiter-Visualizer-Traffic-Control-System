package com.ayngaran.smart_rate_limiter.Service.Algorithms;

import java.util.concurrent.ConcurrentHashMap;

public class FixedWindowAlgorithm implements RateLimiter {
    public static class Bucket{
        long lastFilledTime;
        int requests;
    };
    private final int requestCount;
    private final ConcurrentHashMap<String , FixedWindowAlgorithm.Bucket> buckets=new ConcurrentHashMap<>();
    public FixedWindowAlgorithm(int requestCount) {
        this.requestCount = requestCount;
    }

    @Override
    public boolean allowRequest(String id) {
        Bucket b = buckets.computeIfAbsent(id,k->{
            Bucket x = new Bucket();
            x.requests =  requestCount;
            x.lastFilledTime = System.currentTimeMillis();
            return x;
        });
        synchronized (b){
            refill(b);
            if(b.requests>=1){
                b.requests--;
                return true;
            }return false;
        }


    };
    public void refill(Bucket b){
        long now = System.currentTimeMillis();
        long last = b.lastFilledTime;
        if((now)-(last)>=60000){
            b.requests = requestCount;
            b.lastFilledTime = now;
        }
    }
}
