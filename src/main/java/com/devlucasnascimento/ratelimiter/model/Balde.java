package com.devlucasnascimento.ratelimiter.model;

import java.time.LocalDateTime;

public class Balde {

    private int fichas;
    private LocalDateTime ultimaReposicao;
    private int capacidadeMaxima;

    public Balde(int fichas, int capacidadeMaxima) {
        this.fichas = fichas;
        this.capacidadeMaxima = capacidadeMaxima;
        this.ultimaReposicao = LocalDateTime.now();
    }

    public int getFichas() {
        return fichas;
    }

    public LocalDateTime getUltimaReposicao() {
        return ultimaReposicao;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }
}
