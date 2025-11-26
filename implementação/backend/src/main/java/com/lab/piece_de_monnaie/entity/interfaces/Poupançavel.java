package com.lab.piece_de_monnaie.entity.interfaces;
// que nome é melhor?
public interface Poupançavel {
    boolean possuiSaldoSuficiente(Long montante);
    void descontarMontante(Long montante);
}
