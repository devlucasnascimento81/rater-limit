package com.devlucasnascimento.ratelimiter;

import com.devlucasnascimento.ratelimiter.server.RateLimiterServer;
import com.devlucasnascimento.ratelimiter.service.RateLimiter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        RateLimiter rateLimiter = new RateLimiter(5, 5);
        RateLimiterServer server = new RateLimiterServer(rateLimiter);
        server.start();
    }
}
