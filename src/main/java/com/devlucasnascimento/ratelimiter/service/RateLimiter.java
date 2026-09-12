package com.devlucasnascimento.ratelimiter.service;

import com.devlucasnascimento.ratelimiter.model.Bucket;

import java.util.concurrent.ConcurrentHashMap;

public class
RateLimiter {
    private final ConcurrentHashMap<String, Bucket> bucketHashMap;
    private final int initialTokens;
    private final int maxCapacity;

    public RateLimiter(int initialTokens, int maxCapacity) {
        this.initialTokens = initialTokens;
        this.maxCapacity = maxCapacity;
        this.bucketHashMap = new ConcurrentHashMap<>();
    }

    public boolean allowRequest(String key){
        Bucket bucket = bucketHashMap.computeIfAbsent(key, s -> new Bucket(initialTokens, maxCapacity));
        bucket.refill();
        return bucket.consumeToken();
    }

}
