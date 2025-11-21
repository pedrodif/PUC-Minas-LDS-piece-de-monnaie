package com.lab.piece_de_monnaie.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VantagemRequest(
        @NotNull
        Long valor,
        @NotBlank
        String descricao,
        String nomeImagem,
        String imagem) {
}
