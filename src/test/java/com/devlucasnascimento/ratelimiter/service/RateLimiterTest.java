package com.devlucasnascimento.ratelimiter.service;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RateLimiterTest {
    @Test
    public void firstRequest() {
        RateLimiter rateLimiter = new RateLimiter(3, 3);

        assertTrue(rateLimiter.allowRequest("Cliente1"));
    }
    @Test
    public  void fourthRequest(){
        RateLimiter rateLimiter1 = new RateLimiter(3,3);

        rateLimiter1.allowRequest("cliente2");
        rateLimiter1.allowRequest("cliente2");
        rateLimiter1.allowRequest("cliente2");

        assertFalse(rateLimiter1.allowRequest("cliente2"));

    }
    @Test
    public void diferentKeysHaveIndependentBuckets(){
        RateLimiter rateLimiter = new RateLimiter(3, 3);

        rateLimiter.allowRequest("Cliente1");
        rateLimiter.allowRequest("Cliente1");
        rateLimiter.allowRequest("Cliente1");


        assertTrue(rateLimiter.allowRequest("Cliente2"));


    }

}
