package com.ayngaran.smart_rate_limiter.Service.Algorithms;

public interface RateLimiter {
    // added capacity and rate params to support dynamic simulation
    boolean allowRequest(String id, int capacity, int rate);
}