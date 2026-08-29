package com.devlucasnascimento.ratelimiter.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Bucket {

    private int tokens;
    private LocalDateTime lastRefil;
    private int maxCapacity;

    public Bucket(int tokens, int maxCapacity) {
        this.tokens = tokens;
        this.maxCapacity = maxCapacity;
        this.lastRefil = LocalDateTime.now();
    }

    public int getTokens() {
        return tokens;
    }

    public LocalDateTime getLastRefil() {
        return lastRefil;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public boolean consumeToken(){
        if (tokens > 0){
            tokens--;
            return true;
        }
        return false;
    }

    public void refill(){
        int taxa = 1;
        long segundosPassados = ChronoUnit.SECONDS.between(lastRefil, LocalDateTime.now());
        int fichasaRepor = (int) (segundosPassados / taxa);
        tokens = Math.min(tokens + fichasaRepor, maxCapacity);
        lastRefil = LocalDateTime.now();
    }
}
