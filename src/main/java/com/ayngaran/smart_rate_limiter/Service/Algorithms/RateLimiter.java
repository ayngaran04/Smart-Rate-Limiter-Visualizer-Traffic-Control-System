package com.ayngaran.smart_rate_limiter.Service.Algorithms;

public interface RateLimiter {
    boolean allowRequest(String id);
}
