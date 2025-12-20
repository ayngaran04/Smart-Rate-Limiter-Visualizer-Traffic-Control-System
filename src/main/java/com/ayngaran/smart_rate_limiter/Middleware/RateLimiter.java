package com.ayngaran.smart_rate_limiter.Middleware;

public interface RateLimiter {
    boolean allowRequest(String id);
}
