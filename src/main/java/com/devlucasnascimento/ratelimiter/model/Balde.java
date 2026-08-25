package com.devlucasnascimento.ratelimiter.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

    public boolean consumirFicha(){
        if (fichas > 0){
            fichas --;
            return true;
        }
        return false;
    }

    public void reporFichas(){
        int taxa = 1;
        long segundosPassados = ChronoUnit.SECONDS.between(ultimaReposicao, LocalDateTime.now());
        int fichasaRepor = (int) (segundosPassados / taxa);
        fichas = Math.min(fichas + fichasaRepor, capacidadeMaxima);
        ultimaReposicao = LocalDateTime.now();
    }
}
